package raf.console.chitalka.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookmarkEntity")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val bookId: Long,
    val chapterIndex: Long,
    val offset: Long,
    val label: String?,
    val createdAt: Long,
    val progress: Float
)
