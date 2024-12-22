package com.byteflipper.everbook.presentation.screens.about.nested.license_info.data

import androidx.compose.runtime.Immutable
import com.mikepenz.aboutlibraries.entity.Library

@Immutable
data class LicenseInfoState(
    val license: Library? = null,
)