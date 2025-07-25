/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:OptIn(ExperimentalMaterialApi::class)

package raf.console.chitalka.ui.history

import android.os.Parcelable
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.presentation.history.HistoryContent
import raf.console.chitalka.presentation.navigator.LocalNavigator
import raf.console.chitalka.ui.book_info.BookInfoScreen
import raf.console.chitalka.ui.library.LibraryScreen
import raf.console.chitalka.ui.reader.ReaderScreen

@Parcelize
object HistoryScreen : Screen, Parcelable {

    @IgnoredOnParcel
    const val DELETE_WHOLE_HISTORY_DIALOG = "delete_whole_history_dialog"

    @IgnoredOnParcel
    val refreshListChannel: Channel<Long> = Channel(Channel.CONFLATED)

    @IgnoredOnParcel
    val insertHistoryChannel: Channel<Int> = Channel(Channel.CONFLATED)

    @IgnoredOnParcel
    private var initialIndex = 0

    @IgnoredOnParcel
    private var initialOffset = 0

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = hiltViewModel<HistoryModel>()

        val state = screenModel.state.collectAsStateWithLifecycle()

        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = initialIndex,
            initialFirstVisibleItemScrollOffset = initialOffset
        )
        DisposableEffect(Unit) {
            onDispose {
                initialIndex = listState.firstVisibleItemIndex
                initialOffset = listState.firstVisibleItemScrollOffset
            }
        }

        val snackbarState = remember { SnackbarHostState() }
        val focusRequester = remember { FocusRequester() }
        val refreshState = rememberPullRefreshState(
            refreshing = state.value.isRefreshing,
            onRefresh = {
                screenModel.onEvent(
                    HistoryEvent.OnRefreshList(
                        loading = false,
                        hideSearch = true
                    )
                )
            }
        )

        HistoryContent(
            refreshState = refreshState,
            snackbarState = snackbarState,
            listState = listState,
            history = state.value.history,
            dialog = state.value.dialog,
            canScrollBackward = listState.canScrollBackward,
            showSearch = state.value.showSearch,
            isLoading = state.value.isLoading,
            isRefreshing = state.value.isRefreshing,
            historyIsEmpty = state.value.history.isEmpty(),
            focusRequester = focusRequester,
            searchQuery = state.value.searchQuery,
            searchVisibility = screenModel::onEvent,
            requestFocus = screenModel::onEvent,
            searchQueryChange = screenModel::onEvent,
            search = screenModel::onEvent,
            deleteHistoryEntry = screenModel::onEvent,
            showDeleteWholeHistoryDialog = screenModel::onEvent,
            actionDeleteWholeHistoryDialog = screenModel::onEvent,
            dismissDialog = screenModel::onEvent,
            navigateToLibrary = {
                navigator.push(LibraryScreen, saveInBackStack = false)
            },
            navigateToBookInfo = {
                navigator.push(BookInfoScreen(bookId = it))
            },
            navigateToReader = {
                insertHistoryChannel.trySend(it)
                navigator.push(ReaderScreen(it))
            }
        )
    }
}