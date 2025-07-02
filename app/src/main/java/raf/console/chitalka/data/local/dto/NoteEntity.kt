package raf.console.chitalka.data.local.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "NoteEntity",
    foreignKeys = [
        ForeignKey(
            entity = BookmarkEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookmarkId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["bookmarkId"])]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val bookId: Long,
    val chapterIndex: Long,
    val offsetStart: Long,
    val offsetEnd: Long,
    val content: String,
    val createdAt: Long,
    val bookmarkId: Long? = null // привязка к закладке (необязательно)
)