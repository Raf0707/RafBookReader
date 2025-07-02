/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.util

import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: () -> Unit
): Modifier {
    return this.combinedClickable(
        indication = null,
        interactionSource = null,
        enabled = enabled,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick,
        onClick = onClick
    )
}