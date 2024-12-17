package com.byteflipper.book_story.domain.use_case.history

import com.byteflipper.book_story.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteWholeHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute() {
        repository.deleteWholeHistory()
    }
}