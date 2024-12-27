package raf.console.chitalka.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import raf.console.chitalka.domain.util.UIText

@Immutable
data class FontWithName(
    val id: String,
    val fontName: UIText,
    val font: FontFamily
)