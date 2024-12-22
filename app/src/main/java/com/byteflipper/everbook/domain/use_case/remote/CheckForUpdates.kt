package com.byteflipper.everbook.domain.use_case.remote

import com.byteflipper.everbook.data.remote.dto.LatestReleaseInfo
import com.byteflipper.everbook.domain.repository.RemoteRepository
import javax.inject.Inject

class CheckForUpdates @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend fun execute(postNotification: Boolean): LatestReleaseInfo? {
        return repository.checkForUpdates(postNotification)
    }
}