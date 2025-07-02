package raf.console.chitalka.domain.use_case.bookmark

import kotlinx.coroutines.flow.Flow
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.repository.BookmarkRepository
import javax.inject.Inject

class ObserveAllBookmarks @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke(): Flow<List<Bookmark>> = repository.observeAllBookmarks()
}
