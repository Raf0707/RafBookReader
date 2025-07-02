package raf.console.chitalka.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import raf.console.chitalka.data.local.room.BookmarkDao
import raf.console.chitalka.data.mapper.bookmark.BookmarkMapper
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.repository.BookmarkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val mapper: BookmarkMapper
) : BookmarkRepository {

    override suspend fun insertBookmark(bookmark: Bookmark): Long {
        return bookmarkDao.insert(mapper.toEntity(bookmark))
    }

    override suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarkDao.update(mapper.toEntity(bookmark))
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.delete(mapper.toEntity(bookmark))
    }

    override suspend fun upsertBookmark(bookmark: Bookmark) {
        bookmarkDao.upsert(mapper.toEntity(bookmark))
    }

    override suspend fun getBookmarkById(id: Long): Bookmark? {
        return bookmarkDao.getById(id.toInt())?.let { mapper.toDomain(it) }
    }

    override suspend fun getAllBookmarks(): List<Bookmark> {
        return bookmarkDao.getAll().map { mapper.toDomain(it) }
    }

    override suspend fun countBookmarks(): Int {
        return bookmarkDao.count()
    }

    override fun observeAllBookmarks(): Flow<List<Bookmark>> {
        return bookmarkDao.observeAll().map { list -> list.map { mapper.toDomain(it) } }
    }

    override fun observeBookmarksForBook(bookId: Long): Flow<List<Bookmark>> {
        return bookmarkDao.observeForBook(bookId.toInt()).map { list -> list.map { mapper.toDomain(it) } }
    }
}
