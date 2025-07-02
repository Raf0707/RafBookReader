/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("SameReturnValue")

package raf.console.chitalka.presentation.core.constants

import androidx.compose.ui.graphics.Color
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.library.category.Category
import raf.console.chitalka.domain.reader.ColorPreset
import raf.console.chitalka.domain.ui.UIText

// Main State
fun provideMainState() = "main_state"

// Empty Book
fun provideEmptyBook() = Book(
    id = -1,
    title = "",
    author = UIText.StringValue(""),
    description = null,
    filePath = "",
    coverImage = null,
    scrollIndex = 0,
    scrollOffset = 0,
    progress = 0f,
    lastOpened = null,
    category = Category.READING,
    categoryIds = listOf(0)
)

// Default Color Preset
fun provideDefaultColorPreset() = ColorPreset(
    id = -1,
    name = null,
    backgroundColor = Color(0xFFFAF8FF), // Blue Light Surface (hardcoded)
    fontColor = Color(0xFF44464F), // Blue Light OnSurfaceVariant (hardcoded)
    isSelected = false
)