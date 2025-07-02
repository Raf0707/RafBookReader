package raf.console.chitalka.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import raf.console.chitalka.data.local.dto.BookmarkEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: BookmarkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookmarks: List<BookmarkEntity>)

    @Upsert
    suspend fun upsert(bookmark: BookmarkEntity)

    @Upsert
    suspend fun upsertAll(bookmarks: List<BookmarkEntity>)

    @Update
    suspend fun update(bookmark: BookmarkEntity)

    @Update
    suspend fun updateAll(bookmarks: List<BookmarkEntity>)

    @Delete
    suspend fun delete(bookmark: BookmarkEntity)

    @Query("DELETE FROM BookmarkEntity WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)

    @Query("SELECT * FROM BookmarkEntity ORDER BY createdAt DESC")
    suspend fun getAll(): List<BookmarkEntity>

    @Query("SELECT * FROM BookmarkEntity WHERE bookId = :bookId ORDER BY chapterIndex")
    suspend fun getForBook(bookId: Int): List<BookmarkEntity>

    @Query("SELECT * FROM BookmarkEntity WHERE id = :id")
    suspend fun getById(id: Int): BookmarkEntity?

    @Query("SELECT COUNT(*) FROM BookmarkEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM BookmarkEntity ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM BookmarkEntity WHERE bookId = :bookId ORDER BY chapterIndex")
    fun observeForBook(bookId: Int): Flow<List<BookmarkEntity>>


}
