/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.history.GroupedHistory
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar
import raf.console.chitalka.presentation.core.constants.providePrimaryScrollbar
import raf.console.chitalka.presentation.core.util.LocalActivity
import raf.console.chitalka.presentation.settings.components.SettingsSubcategoryTitle
import raf.console.chitalka.ui.history.HistoryEvent
import raf.console.chitalka.ui.theme.DefaultTransition

@Composable
fun HistoryLayout(
    listState: LazyListState,
    history: List<GroupedHistory>,
    snackbarState: SnackbarHostState,
    isLoading: Boolean,
    isRefreshing: Boolean,
    deleteHistoryEntry: (HistoryEvent.OnDeleteHistoryEntry) -> Unit,
    navigateToBookInfo: (Int) -> Unit,
    navigateToReader: (Int) -> Unit,
) {
    val context = LocalActivity.current

    DefaultTransition(visible = !isLoading) {
        LazyColumnWithScrollbar(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            scrollbarSettings = providePrimaryScrollbar(false)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            history.forEachIndexed { index, groupedHistory ->
                item(key = groupedHistory.title) {
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    SettingsSubcategoryTitle(
                        modifier = Modifier.animateItem(),
                        title = when (groupedHistory.title) {
                            "today" -> stringResource(id = R.string.today)
                            "yesterday" -> stringResource(id = R.string.yesterday)
                            else -> groupedHistory.title
                        },
                        padding = 16.dp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(
                    groupedHistory.history,
                    key = { it.id }
                ) { historyEntry ->
                    HistoryItem(
                        historyEntry = historyEntry,
                        isRefreshing = isRefreshing,
                        onBodyClick = {
                            navigateToBookInfo(historyEntry.bookId)
                        },
                        onTitleClick = {
                            navigateToReader(historyEntry.bookId)
                        },
                        onDeleteClick = {
                            deleteHistoryEntry(
                                HistoryEvent.OnDeleteHistoryEntry(
                                    history = historyEntry,
                                    snackbarState = snackbarState,
                                    context = context,
                                )
                            )
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}