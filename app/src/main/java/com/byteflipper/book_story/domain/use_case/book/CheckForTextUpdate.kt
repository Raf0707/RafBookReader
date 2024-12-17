package com.byteflipper.book_story.domain.use_case.book

import com.byteflipper.book_story.domain.model.Chapter
import com.byteflipper.book_story.domain.repository.BookRepository
import com.byteflipper.book_story.domain.util.Resource
import javax.inject.Inject

class CheckForTextUpdate @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookId: Int): Resource<Pair<List<String>, List<Chapter>>?> {
        return repository.checkForTextUpdate(bookId)
    }
}