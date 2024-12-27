package raf.console.chitalka.domain.use_case.remote

import raf.console.chitalka.data.remote.dto.LatestReleaseInfo
import raf.console.chitalka.domain.repository.RemoteRepository
import javax.inject.Inject

class CheckForUpdates @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend fun execute(postNotification: Boolean): LatestReleaseInfo? {
        return repository.checkForUpdates(postNotification)
    }
}