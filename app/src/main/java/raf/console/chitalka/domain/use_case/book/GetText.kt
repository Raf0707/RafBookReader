package raf.console.chitalka.domain.use_case.book

import androidx.compose.ui.text.AnnotatedString
import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class GetText @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(textPath: String): List<AnnotatedString> {
        return repository.getBookText(textPath = textPath)
    }
}