/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.IconButton
import raf.console.chitalka.presentation.core.components.common.SearchTextField
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.components.top_bar.TopAppBar
import raf.console.chitalka.presentation.core.components.top_bar.TopAppBarData
import raf.console.chitalka.presentation.navigator.NavigatorIconButton
import raf.console.chitalka.ui.history.HistoryEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(
    canScrollBackward: Boolean,
    showSearch: Boolean,
    isLoading: Boolean,
    isRefreshing: Boolean,
    historyIsNotEmpty: Boolean,
    focusRequester: FocusRequester,
    searchQuery: String,
    searchVisibility: (HistoryEvent.OnSearchVisibility) -> Unit,
    requestFocus: (HistoryEvent.OnRequestFocus) -> Unit,
    searchQueryChange: (HistoryEvent.OnSearchQueryChange) -> Unit,
    showDeleteWholeHistoryDialog: (HistoryEvent.OnShowDeleteWholeHistoryDialog) -> Unit,
    search: (HistoryEvent.OnSearch) -> Unit,
) {
    TopAppBar(
        scrollBehavior = null,
        isTopBarScrolled = canScrollBackward,

        shownTopBar = when {
            showSearch -> 1
            else -> 0
        },
        topBars = listOf(
            TopAppBarData(
                contentID = 0,
                contentNavigationIcon = {},
                contentTitle = {
                    StyledText(stringResource(id = R.string.history_screen))
                },
                contentActions = {
                    IconButton(
                        icon = Icons.Default.Search,
                        contentDescription = R.string.search_content_desc,
                        disableOnClick = true,
                    ) {
                        searchVisibility(HistoryEvent.OnSearchVisibility(true))
                    }
                    IconButton(
                        icon = Icons.Outlined.DeleteSweep,
                        contentDescription = R.string.delete_whole_history_content_desc,
                        disableOnClick = false,
                        enabled = !isLoading
                                && !isRefreshing
                                && historyIsNotEmpty
                    ) {
                        showDeleteWholeHistoryDialog(
                            HistoryEvent.OnShowDeleteWholeHistoryDialog
                        )
                    }
                    NavigatorIconButton()
                }
            ),

            TopAppBarData(
                contentID = 1,
                contentNavigationIcon = {
                    IconButton(
                        icon = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = R.string.exit_search_content_desc,
                        disableOnClick = true
                    ) {
                        searchVisibility(HistoryEvent.OnSearchVisibility(false))
                    }
                },
                contentTitle = {
                    SearchTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .onGloballyPositioned {
                                requestFocus(HistoryEvent.OnRequestFocus(focusRequester))
                            },
                        initialQuery = searchQuery,
                        onQueryChange = {
                            searchQueryChange(HistoryEvent.OnSearchQueryChange(it))
                        },
                        onSearch = {
                            search(HistoryEvent.OnSearch)
                        }
                    )
                },
                contentActions = {
                    NavigatorIconButton()
                }
            )
        )
    )
}