/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.about

import android.content.Context
import androidx.compose.runtime.Immutable

@Immutable
sealed class AboutEvent {
    data class OnNavigateToBrowserPage(
        val page: String,
        val context: Context
    ) : AboutEvent()
}