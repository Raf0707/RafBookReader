package com.byteflipper.book_story.domain.model

import androidx.compose.runtime.Immutable
import com.byteflipper.book_story.domain.util.UIText

@Immutable
data class Credit(
    val name: String,
    val source: String?,
    val credits: List<UIText>,
    val website: String?
)
