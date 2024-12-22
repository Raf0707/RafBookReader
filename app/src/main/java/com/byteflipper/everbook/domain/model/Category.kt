package com.byteflipper.everbook.domain.model

import androidx.compose.runtime.Immutable
import com.byteflipper.everbook.domain.util.Selected

@Immutable
enum class Category {
    READING, ALREADY_READ, PLANNING, DROPPED
}

@Immutable
data class CategorizedBooks(
    val category: Category,
    val books: List<Pair<Book, Selected>>
)