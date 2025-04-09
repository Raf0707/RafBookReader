/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.start

import androidx.compose.runtime.Composable

@Composable
fun StartDone(
    navigateToBrowse: () -> Unit,
    navigateToHelp: () -> Unit
) {
    StartDoneScaffold(
        navigateToBrowse = navigateToBrowse,
        navigateToHelp = navigateToHelp
    )
}