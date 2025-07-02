package raf.console.chitalka.domain.use_case.bookmark

import kotlinx.coroutines.flow.Flow
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.repository.BookmarkRepository
import javax.inject.Inject

class InsertBookmark @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark): Long = repository.insertBookmark(bookmark)
}