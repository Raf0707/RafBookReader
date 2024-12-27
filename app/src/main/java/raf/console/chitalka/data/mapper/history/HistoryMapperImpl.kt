package raf.console.chitalka.data.mapper.history

import raf.console.chitalka.data.local.dto.HistoryEntity
import raf.console.chitalka.domain.model.History
import javax.inject.Inject

class HistoryMapperImpl @Inject constructor() : HistoryMapper {
    override suspend fun toHistoryEntity(history: History): HistoryEntity {
        return HistoryEntity(
            id = history.id,
            bookId = history.bookId,
            time = history.time
        )
    }

    override suspend fun toHistory(historyEntity: HistoryEntity): History {
        return History(
            historyEntity.id,
            bookId = historyEntity.bookId,
            book = null,
            time = historyEntity.time
        )
    }
}