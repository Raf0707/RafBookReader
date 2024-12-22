package com.byteflipper.everbook.data.local.notification

import com.byteflipper.everbook.data.remote.dto.LatestReleaseInfo

interface UpdatesNotificationService {

    fun postNotification(releaseInfo: LatestReleaseInfo)

    companion object {
        const val CHANNEL_ID = "updates_channel"
    }
}