/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.ui

import android.content.Context
import android.os.PowerManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
enum class PureDark {
    OFF,
    ON,
    SAVER
}

fun String.toPureDark(): PureDark {
    return PureDark.valueOf(this)
}

@Composable
fun PureDark.isPureDark(context: Context): Boolean {
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    return when (this) {
        PureDark.OFF -> false
        PureDark.ON -> true
        PureDark.SAVER -> powerManager.isPowerSaveMode
    }
}