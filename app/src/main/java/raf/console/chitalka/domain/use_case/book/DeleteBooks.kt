package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.Book
import raf.console.chitalka.domain.repository.BookRepository
import raf.console.chitalka.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteBooks @Inject constructor(
    private val bookRepository: BookRepository,
    private val historyRepository: HistoryRepository
) {

    suspend fun execute(books: List<Book>) {
        bookRepository.deleteBooks(books)
        books.forEach {
            historyRepository.deleteBookHistory(it.id)
        }
    }
}