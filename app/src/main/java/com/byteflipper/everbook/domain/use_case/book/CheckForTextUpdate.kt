package com.byteflipper.everbook.domain.use_case.book

import com.byteflipper.everbook.domain.model.Chapter
import com.byteflipper.everbook.domain.repository.BookRepository
import com.byteflipper.everbook.domain.util.Resource
import javax.inject.Inject

class CheckForTextUpdate @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookId: Int): Resource<Pair<List<String>, List<Chapter>>?> {
        return repository.checkForTextUpdate(bookId)
    }
}