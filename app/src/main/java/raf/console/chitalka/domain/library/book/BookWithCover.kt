/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.library.book

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.util.CoverImage

@Immutable
data class BookWithCover(
    val book: Book,
    val coverImage: CoverImage?
)