/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.browse

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar
import raf.console.chitalka.presentation.core.components.modal_bottom_sheet.ModalBottomSheet
import raf.console.chitalka.presentation.settings.browse.display.BrowseDisplaySubcategory
import raf.console.chitalka.presentation.settings.browse.filter.BrowseFilterSubcategory
import raf.console.chitalka.presentation.settings.browse.sort.BrowseSortSubcategory
import raf.console.chitalka.ui.browse.BrowseEvent

private var initialPage = 0

@Composable
fun BrowseFilterBottomSheet(
    dismissBottomSheet: (BrowseEvent.OnDismissBottomSheet) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage) { 3 }
    DisposableEffect(Unit) { onDispose { initialPage = pagerState.currentPage } }

    ModalBottomSheet(
        hasFixedHeight = true,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        dragHandle = {},
        onDismissRequest = {
            dismissBottomSheet(BrowseEvent.OnDismissBottomSheet)
        },
        sheetGesturesEnabled = false
    ) {
        BrowseFilterBottomSheetTabRow(
            currentPage = pagerState.currentPage,
            scrollToPage = {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        )

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            when (page) {
                0 -> {
                    LazyColumnWithScrollbar(modifier = Modifier.fillMaxSize()) {
                        BrowseFilterSubcategory(
                            showTitle = false,
                            showDivider = false
                        )
                    }
                }

                1 -> {
                    LazyColumnWithScrollbar(modifier = Modifier.fillMaxSize()) {
                        BrowseSortSubcategory(
                            showTitle = false,
                            showDivider = false
                        )
                    }
                }

                2 -> {
                    LazyColumnWithScrollbar(modifier = Modifier.fillMaxSize()) {
                        BrowseDisplaySubcategory(
                            showTitle = false,
                            showDivider = false
                        )
                    }
                }
            }
        }
    }
}