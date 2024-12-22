package com.byteflipper.everbook.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import com.byteflipper.everbook.domain.util.UIText

@Immutable
data class FontWithName(
    val id: String,
    val fontName: UIText,
    val font: FontFamily
)