package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.Book
import raf.console.chitalka.domain.repository.BookRepository
import raf.console.chitalka.domain.util.CoverImage
import javax.inject.Inject

class UpdateCoverImageOfBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookWithOldCover: Book, newCoverImage: CoverImage?) {
        repository.updateCoverImageOfBook(bookWithOldCover, newCoverImage)
    }
}