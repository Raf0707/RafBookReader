/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.crash

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar

@Composable
fun CrashLayout(
    content: LazyListScope.() -> Unit
) {
    LazyColumnWithScrollbar(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp)
    ) {
        content()
    }
}