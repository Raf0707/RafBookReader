package com.byteflipper.book_story.domain.repository

import com.byteflipper.book_story.data.remote.dto.LatestReleaseInfo

interface RemoteRepository {

    suspend fun checkForUpdates(
        postNotification: Boolean
    ): LatestReleaseInfo?
}