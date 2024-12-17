package com.byteflipper.book_story.domain.repository

import com.byteflipper.book_story.domain.model.History

interface HistoryRepository {

    suspend fun insertHistory(
        history: History
    )

    suspend fun getHistory(): List<History>

    suspend fun getLatestBookHistory(
        bookId: Int
    ): History?

    suspend fun deleteWholeHistory()

    suspend fun deleteBookHistory(
        bookId: Int
    )

    suspend fun deleteHistory(
        history: History
    )
}