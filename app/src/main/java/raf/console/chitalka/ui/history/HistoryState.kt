/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.history

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.history.GroupedHistory
import raf.console.chitalka.domain.util.Dialog

@Immutable
data class HistoryState(
    val history: List<GroupedHistory> = emptyList(),

    val isRefreshing: Boolean = false,
    val isLoading: Boolean = true,

    val showSearch: Boolean = false,
    val searchQuery: String = "",
    val hasFocused: Boolean = false,

    val dialog: Dialog? = null
)