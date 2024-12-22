package com.byteflipper.everbook.domain.use_case.book

import com.byteflipper.everbook.domain.repository.BookRepository
import javax.inject.Inject

class ResetCoverImage @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookId: Int): Boolean {
        return repository.resetCoverImage(bookId)
    }
}