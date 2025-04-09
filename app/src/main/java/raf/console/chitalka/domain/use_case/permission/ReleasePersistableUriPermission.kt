/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.permission

import android.net.Uri
import raf.console.chitalka.domain.repository.PermissionRepository
import javax.inject.Inject

class ReleasePersistableUriPermission @Inject constructor(
    private val repository: PermissionRepository
) {

    suspend fun execute(uri: Uri) {
        repository.releasePersistableUriPermission(uri)
    }
}