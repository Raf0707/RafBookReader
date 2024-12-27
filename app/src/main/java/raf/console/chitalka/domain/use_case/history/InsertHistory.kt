package raf.console.chitalka.domain.use_case.history

import raf.console.chitalka.domain.model.History
import raf.console.chitalka.domain.repository.HistoryRepository
import javax.inject.Inject

class InsertHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute(history: History) {
        repository.insertHistory(history)
    }
}