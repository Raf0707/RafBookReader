package com.byteflipper.book_story.domain.use_case.remote

import com.byteflipper.book_story.data.remote.dto.LatestReleaseInfo
import com.byteflipper.book_story.domain.repository.RemoteRepository
import javax.inject.Inject

class CheckForUpdates @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend fun execute(postNotification: Boolean): LatestReleaseInfo? {
        return repository.checkForUpdates(postNotification)
    }
}