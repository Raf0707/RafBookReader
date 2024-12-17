package com.byteflipper.book_story.data.mapper.history

import com.byteflipper.book_story.data.local.dto.HistoryEntity
import com.byteflipper.book_story.domain.model.History

interface HistoryMapper {
    suspend fun toHistoryEntity(history: History): HistoryEntity

    suspend fun toHistory(historyEntity: HistoryEntity): History
}