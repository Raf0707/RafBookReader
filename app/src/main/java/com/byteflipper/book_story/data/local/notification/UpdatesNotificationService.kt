package com.byteflipper.book_story.data.local.notification

import com.byteflipper.book_story.data.remote.dto.LatestReleaseInfo

interface UpdatesNotificationService {

    fun postNotification(releaseInfo: LatestReleaseInfo)

    companion object {
        const val CHANNEL_ID = "updates_channel"
    }
}