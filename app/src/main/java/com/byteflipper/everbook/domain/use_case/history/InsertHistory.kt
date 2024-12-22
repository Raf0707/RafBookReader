package com.byteflipper.everbook.domain.use_case.history

import com.byteflipper.everbook.domain.model.History
import com.byteflipper.everbook.domain.repository.HistoryRepository
import javax.inject.Inject

class InsertHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute(history: History) {
        repository.insertHistory(history)
    }
}