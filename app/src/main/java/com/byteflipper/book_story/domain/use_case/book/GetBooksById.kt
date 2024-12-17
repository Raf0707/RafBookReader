package com.byteflipper.book_story.domain.use_case.book

import com.byteflipper.book_story.domain.model.Book
import com.byteflipper.book_story.domain.repository.BookRepository
import javax.inject.Inject

class GetBooksById @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(ids: List<Int>): List<Book> {
        return repository.getBooksById(ids)
    }
}