package com.byteflipper.book_story.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.byteflipper.book_story.domain.util.Selected

@Immutable
data class ColorPreset(
    val id: Int,
    val name: String?,
    val backgroundColor: Color,
    val fontColor: Color,
    val isSelected: Selected
)