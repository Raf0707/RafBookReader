/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.library.book.SelectableBook
import raf.console.chitalka.domain.use_case.book.DeleteBooks
import raf.console.chitalka.domain.use_case.book.GetBooks
import raf.console.chitalka.domain.use_case.book.SetBookCategories
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.ui.browse.BrowseScreen
import raf.console.chitalka.ui.history.HistoryScreen
import javax.inject.Inject

@HiltViewModel
class LibraryModel @Inject constructor(
    private val getBooks: GetBooks,
    private val deleteBooks: DeleteBooks,
    private val setBookCategories: SetBookCategories
) : ViewModel() {

    private val mutex = Mutex()

    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            onEvent(
                LibraryEvent.OnRefreshList(
                    loading = true,
                    hideSearch = true
                )
            )
        }

        /* Observe channel - - - - - - - - - - - */
        viewModelScope.launch(Dispatchers.IO) {
            LibraryScreen.refreshListChannel.receiveAsFlow().collectLatest {
                delay(it)
                yield()

                onEvent(
                    LibraryEvent.OnRefreshList(
                        loading = false,
                        hideSearch = false
                    )
                )
            }
        }
        /* - - - - - - - - - - - - - - - - - - - */
    }

    private var refreshJob: Job? = null
    private var searchQueryChange: Job? = null

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnRefreshList -> {
                refreshJob?.cancel()
                refreshJob = viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            isRefreshing = true,
                            isLoading = event.loading,
                            showSearch = if (event.hideSearch) false else it.showSearch
                        )
                    }

                    yield()
                    getBooksFromDatabase()

                    delay(500)
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            isLoading = false
                        )
                    }
                }
            }

            is LibraryEvent.OnSearchVisibility -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (!event.show) {
                        onEvent(
                            LibraryEvent.OnRefreshList(
                                loading = false,
                                hideSearch = true
                            )
                        )
                    } else {
                        _state.update {
                            it.copy(
                                searchQuery = "",
                                hasFocused = false
                            )
                        }
                    }

                    _state.update {
                        it.copy(
                            showSearch = event.show
                        )
                    }
                }
            }

            is LibraryEvent.OnSearchQueryChange -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            searchQuery = event.query
                        )
                    }
                    searchQueryChange?.cancel()
                    searchQueryChange = launch(Dispatchers.IO) {
                        delay(500)
                        yield()
                        onEvent(LibraryEvent.OnSearch)
                    }
                }
            }

            is LibraryEvent.OnSearch -> {
                viewModelScope.launch(Dispatchers.IO) {
                    onEvent(
                        LibraryEvent.OnRefreshList(
                            loading = false,
                            hideSearch = false
                        )
                    )
                }
            }

            is LibraryEvent.OnRequestFocus -> {
                viewModelScope.launch(Dispatchers.Main) {
                    if (!_state.value.hasFocused) {
                        event.focusRequester.requestFocus()
                        _state.update {
                            it.copy(
                                hasFocused = true
                            )
                        }
                    }
                }
            }

            is LibraryEvent.OnClearSelectedBooks -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            books = it.books.map { book -> book.copy(selected = false) },
                            hasSelectedItems = false
                        )
                    }
                }
            }

            is LibraryEvent.OnSelectBook -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val editedList = _state.value.books.map {
                        if (it.data.id == event.id) it.copy(selected = event.select ?: !it.selected)
                        else it
                    }

                    _state.update {
                        it.copy(
                            books = editedList,
                            selectedItemsCount = editedList.filter { book -> book.selected }.size,
                            hasSelectedItems = editedList.any { book -> book.selected }
                        )
                    }
                }
            }

            is LibraryEvent.OnShowCategoriesDialog -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            dialog = LibraryScreen.MOVE_DIALOG
                        )
                    }
                }
            }

            is LibraryEvent.OnActionSetCategoriesDialog -> {
                viewModelScope.launch {
                    val ids = _state.value.books.filter { it.selected }.map { it.data.id }
                    ids.forEach { id ->
                        setBookCategories(id, event.categoryIds)
                    }

                    _state.update {
                        it.copy(
                            books = it.books.map { book ->
                                if (!book.selected) return@map book
                                book.copy(
                                    selected = false
                                )
                            },
                            hasSelectedItems = false,
                            dialog = null
                        )
                    }

                    HistoryScreen.refreshListChannel.trySend(0)
                    BrowseScreen.refreshListChannel.trySend(Unit)

                    onEvent(
                        LibraryEvent.OnRefreshList(
                            loading = false,
                            hideSearch = false
                        )
                    )
                }
            }

            is LibraryEvent.OnShowDeleteDialog -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            dialog = LibraryScreen.DELETE_DIALOG
                        )
                    }
                }
            }

            is LibraryEvent.OnActionDeleteDialog -> {
                viewModelScope.launch {
                    deleteBooks.execute(
                        _state.value.books.mapNotNull {
                            if (!it.selected) return@mapNotNull null
                            it.data
                        }
                    )

                    _state.update {
                        it.copy(
                            books = it.books.filter { book -> !book.selected },
                            hasSelectedItems = false,
                            dialog = null
                        )
                    }

                    HistoryScreen.refreshListChannel.trySend(0)
                    BrowseScreen.refreshListChannel.trySend(Unit)

                    withContext(Dispatchers.Main) {
                        event.context
                            .getString(R.string.books_deleted)
                            .showToast(context = event.context)
                    }
                }
            }

            is LibraryEvent.OnDismissDialog -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            dialog = null
                        )
                    }
                }
            }

            is LibraryEvent.OnShowMoveDialog -> { /* deprecated */ }
            is LibraryEvent.OnActionMoveDialog -> { /* deprecated */ }
        }
    }

    private suspend fun getBooksFromDatabase(
        query: String = if (_state.value.showSearch) _state.value.searchQuery else ""
    ) {
        val books = getBooks
            .execute(query)
            .sortedWith(compareByDescending<Book> { it.lastOpened }.thenBy { it.title })
            .map { book -> SelectableBook(book, false) }

        _state.update {
            it.copy(
                books = books,
                hasSelectedItems = false,
                isLoading = false
            )
        }
    }

    private suspend inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
        mutex.withLock {
            yield()
            this.value = function(this.value)
        }
    }
}