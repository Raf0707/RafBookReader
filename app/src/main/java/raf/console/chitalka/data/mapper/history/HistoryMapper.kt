package raf.console.chitalka.data.mapper.history

import raf.console.chitalka.data.local.dto.HistoryEntity
import raf.console.chitalka.domain.model.History

interface HistoryMapper {
    suspend fun toHistoryEntity(history: History): HistoryEntity

    suspend fun toHistory(historyEntity: HistoryEntity): History
}