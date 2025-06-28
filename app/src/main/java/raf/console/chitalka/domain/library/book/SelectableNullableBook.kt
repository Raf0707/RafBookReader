/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.library.book

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.util.Selected

@Immutable
data class SelectableNullableBook(
    val data: NullableBook,
    val selected: Selected
)