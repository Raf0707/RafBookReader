package raf.console.chitalka.domain.repository

import kotlinx.coroutines.flow.Flow
import raf.console.chitalka.domain.reader.Bookmark

interface BookmarkRepository {
    suspend fun insertBookmark(bookmark: Bookmark): Long
    suspend fun updateBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun upsertBookmark(bookmark: Bookmark)

    suspend fun getBookmarkById(id: Long): Bookmark?
    suspend fun getAllBookmarks(): List<Bookmark>
    suspend fun countBookmarks(): Int

    fun observeAllBookmarks(): Flow<List<Bookmark>>
    fun observeBookmarksForBook(bookId: Long): Flow<List<Bookmark>>
}
