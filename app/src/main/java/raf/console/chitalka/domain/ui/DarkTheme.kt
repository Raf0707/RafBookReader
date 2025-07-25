/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
enum class DarkTheme {
    FOLLOW_SYSTEM,
    OFF,
    ON
}

fun String.toDarkTheme(): DarkTheme {
    return DarkTheme.valueOf(this)
}

@Composable
fun DarkTheme.isDark(): Boolean {
    return when (this) {
        DarkTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkTheme.ON -> true
        DarkTheme.OFF -> false
    }
}