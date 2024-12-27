package raf.console.chitalka.domain.model

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.util.Selected

@Immutable
enum class Category {
    READING, ALREADY_READ, PLANNING, DROPPED
}

@Immutable
data class CategorizedBooks(
    val category: Category,
    val books: List<Pair<Book, Selected>>
)