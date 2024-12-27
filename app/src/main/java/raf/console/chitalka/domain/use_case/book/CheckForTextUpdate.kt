package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.Chapter
import raf.console.chitalka.domain.repository.BookRepository
import raf.console.chitalka.domain.util.Resource
import javax.inject.Inject

class CheckForTextUpdate @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookId: Int): Resource<Pair<List<String>, List<Chapter>>?> {
        return repository.checkForTextUpdate(bookId)
    }
}