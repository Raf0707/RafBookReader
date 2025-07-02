package raf.console.chitalka.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import raf.console.chitalka.data.local.dto.NoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<NoteEntity>)

    @Upsert
    suspend fun upsert(note: NoteEntity)

    @Upsert
    suspend fun upsertAll(notes: List<NoteEntity>)

    @Update
    suspend fun update(note: NoteEntity)

    @Update
    suspend fun updateAll(notes: List<NoteEntity>)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)

    @Query("SELECT * FROM NoteEntity ORDER BY createdAt DESC")
    suspend fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE bookId = :bookId ORDER BY chapterIndex, offsetStart")
    suspend fun getForBook(bookId: Int): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE bookmarkId = :bookmarkId")
    suspend fun getForBookmark(bookmarkId: Int): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun getById(id: Int): NoteEntity?

    @Query("SELECT COUNT(*) FROM NoteEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM NoteEntity ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<NoteEntity>>

    // 🔹 Новый метод для получения заметок по закладке через Flow
    @Query("SELECT * FROM NoteEntity WHERE bookmarkId = :bookmarkId")
    fun observeForBookmark(bookmarkId: Int): Flow<List<NoteEntity>>
}
