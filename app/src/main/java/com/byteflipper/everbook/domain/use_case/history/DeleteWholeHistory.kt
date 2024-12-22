package com.byteflipper.everbook.domain.use_case.history

import com.byteflipper.everbook.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteWholeHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute() {
        repository.deleteWholeHistory()
    }
}