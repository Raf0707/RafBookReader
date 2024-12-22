@file:Suppress("SameReturnValue")

package com.byteflipper.everbook.presentation.core.constants

import androidx.compose.ui.graphics.Color
import com.byteflipper.everbook.domain.model.Book
import com.byteflipper.everbook.domain.model.Category
import com.byteflipper.everbook.domain.model.ColorPreset
import com.byteflipper.everbook.domain.util.UIText

// Main State
fun Constants.provideMainState() = "main_state"

// Empty Book
fun Constants.provideEmptyBook() = Book(
    id = -1,
    title = "",
    author = UIText.StringValue(""),
    description = null,
    textPath = "",
    filePath = "",
    coverImage = null,
    scrollIndex = 0,
    scrollOffset = 0,
    progress = 0f,
    lastOpened = null,
    category = Category.READING
)

// Default Color Preset
fun Constants.provideDefaultColorPreset() = ColorPreset(
    id = -1,
    name = null,
    backgroundColor = Color(0xFFFAF8FF), // Blue Light Surface (hardcoded)
    fontColor = Color(0xFF44464F), // Blue Light OnSurfaceVariant (hardcoded)
    isSelected = false
)