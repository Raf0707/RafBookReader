package com.byteflipper.book_story.domain.use_case.book

import com.byteflipper.book_story.domain.model.Book
import com.byteflipper.book_story.domain.repository.BookRepository
import javax.inject.Inject

class GetBookById @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(id: Int): Book? {
        return repository.getBooksById(listOf(id)).firstOrNull()
    }
}