/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.components.top_bar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Collapsible Scroll Behavior with Lazy List State.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefaults.collapsibleTopAppBarScrollBehavior(
    listState: LazyListState? = null
): Pair<TopAppBarScrollBehavior, LazyListState> {
    val lazyListState = listState ?: rememberLazyListState()

    var canScroll by remember { mutableStateOf(false) }
    val scrollBehavior = exitUntilCollapsedScrollBehavior(
        canScroll = {
            lazyListState.canScrollForward || canScroll
        }
    )

    LaunchedEffect(scrollBehavior.state) {
        snapshotFlow {
            scrollBehavior.state.collapsedFraction
        }.collectLatest { fraction ->
            canScroll = fraction > 0.01f
        }
    }

    return scrollBehavior to lazyListState
}