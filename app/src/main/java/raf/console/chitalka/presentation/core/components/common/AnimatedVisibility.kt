/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Custom [AnimatedVisibility].
 * Prevents bug that animation freezes halfway.
 * FIXED: This bug was fixed. I preserved initial code, if the bug happens again.
 */
@Composable
fun AnimatedVisibility(
    modifier: Modifier = Modifier,
    visible: Boolean,
    enter: EnterTransition,
    exit: ExitTransition,
    content: @Composable () -> Unit
) {
//    val visibleState = remember {
//        MutableTransitionState(visible)
//    }
//
//    LaunchedEffect(visible) {
//        if (visible == visibleState.targetState) {
//            return@LaunchedEffect
//        }
//
//        if (visibleState.isIdle) {
//            visibleState.targetState = visible
//            return@LaunchedEffect
//        }
//
//        snapshotFlow {
//            visibleState.isIdle
//        }.first {
//            if (it) {
//                visibleState.targetState = visible
//            }
//            it
//        }
//    }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        label = ""
    ) {
        content()
    }
}