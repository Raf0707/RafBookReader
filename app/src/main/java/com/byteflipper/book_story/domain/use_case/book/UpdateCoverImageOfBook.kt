package com.byteflipper.book_story.domain.use_case.book

import com.byteflipper.book_story.domain.model.Book
import com.byteflipper.book_story.domain.repository.BookRepository
import com.byteflipper.book_story.domain.util.CoverImage
import javax.inject.Inject

class UpdateCoverImageOfBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookWithOldCover: Book, newCoverImage: CoverImage?) {
        repository.updateCoverImageOfBook(bookWithOldCover, newCoverImage)
    }
}