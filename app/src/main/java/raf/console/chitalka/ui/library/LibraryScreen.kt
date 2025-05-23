/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.library

import android.os.Parcelable
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.category.Category
import raf.console.chitalka.domain.library.category.CategoryWithBooks
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.domain.ui.UIText
import raf.console.chitalka.presentation.library.LibraryContent
import raf.console.chitalka.presentation.navigator.LocalNavigator
import raf.console.chitalka.ui.book_info.BookInfoScreen
import raf.console.chitalka.ui.browse.BrowseScreen
import raf.console.chitalka.ui.history.HistoryScreen
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.reader.ReaderScreen

@Parcelize
object LibraryScreen : Screen, Parcelable {

    @IgnoredOnParcel
    const val MOVE_DIALOG = "move_dialog"

    @IgnoredOnParcel
    const val DELETE_DIALOG = "delete_dialog"

    @IgnoredOnParcel
    val refreshListChannel: Channel<Long> = Channel(Channel.CONFLATED)

    @IgnoredOnParcel
    val scrollToPageCompositionChannel: Channel<Int> = Channel(Channel.CONFLATED)

    @IgnoredOnParcel
    private var initialPage = 0

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val screenModel = hiltViewModel<LibraryModel>()
        val mainModel = hiltViewModel<MainModel>()

        val state = screenModel.state.collectAsStateWithLifecycle()
        val mainState = mainModel.state.collectAsStateWithLifecycle()

        val categories = remember(state.value.books) {
            derivedStateOf {
                listOf(
                    CategoryWithBooks(
                        category = Category.READING,
                        title = UIText.StringResource(R.string.reading_tab),
                        books = state.value.books.filter { it.data.category == Category.READING }
                    ),
                    CategoryWithBooks(
                        category = Category.ALREADY_READ,
                        title = UIText.StringResource(R.string.already_read_tab),
                        books = state.value.books.filter { it.data.category == Category.ALREADY_READ }
                    ),
                    CategoryWithBooks(
                        category = Category.PLANNING,
                        title = UIText.StringResource(R.string.planning_tab),
                        books = state.value.books.filter { it.data.category == Category.PLANNING }
                    ),
                    CategoryWithBooks(
                        category = Category.DROPPED,
                        title = UIText.StringResource(R.string.dropped_tab),
                        books = state.value.books.filter { it.data.category == Category.DROPPED }
                    )
                )
            }
        }

        val focusRequester = remember { FocusRequester() }
        val refreshState = rememberPullRefreshState(
            refreshing = state.value.isRefreshing,
            onRefresh = {
                screenModel.onEvent(
                    LibraryEvent.OnRefreshList(
                        loading = false,
                        hideSearch = true
                    )
                )
            }
        )

        val pagerState = rememberPagerState(
            initialPage = initialPage
        ) { categories.value.count() }
        DisposableEffect(Unit) { onDispose { initialPage = pagerState.currentPage } }

        LaunchedEffect(Unit) {
            scrollToPageCompositionChannel.receiveAsFlow().collectLatest {
                pagerState.animateScrollToPage(it)
            }
        }

        LibraryContent(
            books = state.value.books,
            selectedItemsCount = state.value.selectedItemsCount,
            hasSelectedItems = state.value.hasSelectedItems,
            showSearch = state.value.showSearch,
            searchQuery = state.value.searchQuery,
            bookCount = state.value.books.count(),
            focusRequester = focusRequester,
            pagerState = pagerState,
            isLoading = state.value.isLoading,
            isRefreshing = state.value.isRefreshing,
            doublePressExit = mainState.value.doublePressExit,
            categories = categories.value,
            refreshState = refreshState,
            dialog = state.value.dialog,
            selectBook = screenModel::onEvent,
            searchVisibility = screenModel::onEvent,
            requestFocus = screenModel::onEvent,
            searchQueryChange = screenModel::onEvent,
            search = screenModel::onEvent,
            clearSelectedBooks = screenModel::onEvent,
            showMoveDialog = screenModel::onEvent,
            actionMoveDialog = screenModel::onEvent,
            actionDeleteDialog = screenModel::onEvent,
            showDeleteDialog = screenModel::onEvent,
            dismissDialog = screenModel::onEvent,
            navigateToBrowse = {
                navigator.push(BrowseScreen)
            },
            navigateToReader = {
                HistoryScreen.insertHistoryChannel.trySend(it)
                navigator.push(ReaderScreen(it))
            },
            navigateToBookInfo = {
                navigator.push(BookInfoScreen(bookId = it))
            }
        )
    }
}