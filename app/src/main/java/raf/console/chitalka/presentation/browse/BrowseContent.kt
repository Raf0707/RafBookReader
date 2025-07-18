/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.browse

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import raf.console.chitalka.domain.browse.display.BrowseLayout
import raf.console.chitalka.domain.browse.SelectableFile
import raf.console.chitalka.domain.library.book.SelectableNullableBook
import raf.console.chitalka.domain.util.BottomSheet
import raf.console.chitalka.domain.util.Dialog
import raf.console.chitalka.ui.browse.BrowseEvent
import raf.console.chitalka.ui.main.MainEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BrowseContent(
    files: List<SelectableFile>,
    selectedBooksAddDialog: List<SelectableNullableBook>,
    refreshState: PullRefreshState,
    loadingAddDialog: Boolean,
    dialog: Dialog?,
    bottomSheet: BottomSheet?,
    listState: LazyListState,
    gridState: LazyGridState,
    layout: BrowseLayout,
    gridSize: Int,
    autoGridSize: Boolean,
    includedFilterItems: List<String>,
    pinnedPaths: List<String>,
    canScrollBackList: Boolean,
    canScrollBackGrid: Boolean,
    hasSelectedItems: Boolean,
    selectedItemsCount: Int,
    isRefreshing: Boolean,
    isLoading: Boolean,
    dialogHidden: Boolean,
    filesEmpty: Boolean,
    showSearch: Boolean,
    searchQuery: String,
    focusRequester: FocusRequester,
    searchVisibility: (BrowseEvent.OnSearchVisibility) -> Unit,
    searchQueryChange: (BrowseEvent.OnSearchQueryChange) -> Unit,
    search: (BrowseEvent.OnSearch) -> Unit,
    requestFocus: (BrowseEvent.OnRequestFocus) -> Unit,
    clearSelectedFiles: (BrowseEvent.OnClearSelectedFiles) -> Unit,
    selectFiles: (BrowseEvent.OnSelectFiles) -> Unit,
    selectFile: (BrowseEvent.OnSelectFile) -> Unit,
    dismissBottomSheet: (BrowseEvent.OnDismissBottomSheet) -> Unit,
    showFilterBottomSheet: (BrowseEvent.OnShowFilterBottomSheet) -> Unit,
    showAddDialog: (BrowseEvent.OnShowAddDialog) -> Unit,
    dismissAddDialog: (BrowseEvent.OnDismissAddDialog) -> Unit,
    actionAddDialog: (BrowseEvent.OnActionAddDialog) -> Unit,
    selectAddDialog: (BrowseEvent.OnSelectAddDialog) -> Unit,
    changePinnedPaths: (MainEvent.OnChangeBrowsePinnedPaths) -> Unit,
    navigateToLibrary: () -> Unit,
    navigateToBrowseSettings: () -> Unit,
) {
    BrowseDialog(
        dialog = dialog,
        loadingAddDialog = loadingAddDialog,
        actionAddDialog = actionAddDialog,
        dismissAddDialog = dismissAddDialog,
        selectedBooksAddDialog = selectedBooksAddDialog,
        selectAddDialog = selectAddDialog,
        navigateToLibrary = navigateToLibrary
    )

    BrowseBottomSheet(
        bottomSheet = bottomSheet,
        dismissBottomSheet = dismissBottomSheet
    )

    BrowseScaffold(
        files = files,
        refreshState = refreshState,
        listState = listState,
        gridState = gridState,
        layout = layout,
        gridSize = gridSize,
        autoGridSize = autoGridSize,
        includedFilterItems = includedFilterItems,
        pinnedPaths = pinnedPaths,
        canScrollBackList = canScrollBackList,
        canScrollBackGrid = canScrollBackGrid,
        hasSelectedItems = hasSelectedItems,
        selectedItemsCount = selectedItemsCount,
        isRefreshing = isRefreshing,
        dialogHidden = dialogHidden,
        showSearch = showSearch,
        searchQuery = searchQuery,
        focusRequester = focusRequester,
        searchVisibility = searchVisibility,
        searchQueryChange = searchQueryChange,
        search = search,
        requestFocus = requestFocus,
        clearSelectedFiles = clearSelectedFiles,
        selectFiles = selectFiles,
        isLoading = isLoading,
        filesEmpty = filesEmpty,
        selectFile = selectFile,
        showFilterBottomSheet = showFilterBottomSheet,
        showAddDialog = showAddDialog,
        changePinnedPaths = changePinnedPaths,
        navigateToBrowseSettings = navigateToBrowseSettings
    )

    BrowseBackHandler(
        hasSelectedItems = hasSelectedItems,
        showSearch = showSearch,
        searchVisibility = searchVisibility,
        clearSelectedFiles = clearSelectedFiles,
        navigateToLibrary = navigateToLibrary
    )
}