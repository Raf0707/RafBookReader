package raf.console.chitalka.domain.reader

data class Bookmark(
    val id: Long = 0,
    val bookId: Long,
    val chapterIndex: Long,
    val offset: Long,
    val label: String? = null,
    val createdAt: Long
)
