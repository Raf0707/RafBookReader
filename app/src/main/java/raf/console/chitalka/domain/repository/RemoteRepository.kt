package raf.console.chitalka.domain.repository

import raf.console.chitalka.data.remote.dto.LatestReleaseInfo

interface RemoteRepository {

    suspend fun checkForUpdates(
        postNotification: Boolean
    ): LatestReleaseInfo?
}