package com.byteflipper.everbook.presentation.screens.about.data

import androidx.compose.runtime.Immutable
import com.byteflipper.everbook.data.remote.dto.LatestReleaseInfo

@Immutable
data class AboutState(
    val showUpdateDialog: Boolean = false,
    val alreadyCheckedForUpdates: Boolean = false,
    val updateLoading: Boolean = false,
    val updateInfo: LatestReleaseInfo? = null
)
