package com.byteflipper.book_story.domain.use_case.book

import com.byteflipper.book_story.domain.model.BookWithTextAndCover
import com.byteflipper.book_story.domain.repository.BookRepository
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