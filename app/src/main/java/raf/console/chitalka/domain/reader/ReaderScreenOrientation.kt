/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.reader

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Immutable

@Immutable
enum class ReaderScreenOrientation(val code: Int) {
    DEFAULT(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED),
    FREE(ActivityInfo.SCREEN_ORIENTATION_USER),
    PORTRAIT(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT),
    LANDSCAPE(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE),
    LOCKED_PORTRAIT(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
    LOCKED_LANDSCAPE(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
}

fun String.toReaderScreenOrientation(): ReaderScreenOrientation {
    return ReaderScreenOrientation.valueOf(this)
}