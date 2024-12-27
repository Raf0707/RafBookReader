package raf.console.chitalka.domain.use_case.history

import raf.console.chitalka.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteWholeHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute() {
        repository.deleteWholeHistory()
    }
}