package com.byteflipper.everbook.domain.repository

import com.byteflipper.everbook.data.remote.dto.LatestReleaseInfo

interface RemoteRepository {

    suspend fun checkForUpdates(
        postNotification: Boolean
    ): LatestReleaseInfo?
}