package com.byteflipper.everbook.domain.use_case.book

import com.byteflipper.everbook.domain.model.BookWithTextAndCover
import com.byteflipper.everbook.domain.repository.BookRepository
import javax.inject.Inject

class InsertBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(
        bookWithTextAndCover: BookWithTextAndCover
    ): Boolean {
        return repository.insertBook(bookWithTextAndCover)
    }
}