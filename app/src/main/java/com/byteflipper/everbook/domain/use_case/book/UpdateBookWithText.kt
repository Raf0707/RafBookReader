package com.byteflipper.everbook.domain.use_case.book

import com.byteflipper.everbook.domain.model.BookWithText
import com.byteflipper.everbook.domain.repository.BookRepository
import javax.inject.Inject

class UpdateBookWithText @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookWithText: BookWithText): Boolean {
        return repository.updateBookWithText(bookWithText)
    }
}