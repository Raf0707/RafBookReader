package raf.console.chitalka.domain.reader

data class Note(
    val id: Long = 0,
    val bookId: Long,
    val chapterIndex: Long,
    val offsetStart: Long,
    val offsetEnd: Long,
    val content: String,
    val createdAt: Long,
    val bookmarkId: Long? = null
)
