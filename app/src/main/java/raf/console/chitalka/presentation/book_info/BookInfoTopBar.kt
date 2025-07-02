/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.common.IconButton
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.components.top_bar.TopAppBar
import raf.console.chitalka.presentation.core.components.top_bar.TopAppBarData
import raf.console.chitalka.presentation.navigator.NavigatorBackIconButton
import raf.console.chitalka.ui.book_info.BookInfoEvent
import raf.console.chitalka.ui.theme.DefaultTransition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoTopBar(
    book: Book,
    listState: LazyListState,
    showDetailsBottomSheet: (BookInfoEvent.OnShowDetailsBottomSheet) -> Unit,
    navigateBack: () -> Unit
) {
    val firstVisibleItemIndex = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }

    TopAppBar(
        containerColor = MaterialTheme.colorScheme.surface.copy(0f),
        scrollBehavior = null,
        isTopBarScrolled = listState.canScrollBackward,

        shownTopBar = 0,
        topBars = listOf(
            TopAppBarData(
                contentID = 0,
                contentNavigationIcon = {
                    NavigatorBackIconButton {
                        navigateBack()
                    }
                },
                contentTitle = {
                    DefaultTransition(firstVisibleItemIndex.value > 0) {
                        StyledText(
                            text = book.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 1
                        )
                    }
                },
                contentActions = {
                    IconButton(
                        icon = Icons.Outlined.Info,
                        contentDescription = R.string.file_details,
                        disableOnClick = false,
                        onClick = {
                            showDetailsBottomSheet(BookInfoEvent.OnShowDetailsBottomSheet)
                        }
                    )
                }
            )
        )
    )
}