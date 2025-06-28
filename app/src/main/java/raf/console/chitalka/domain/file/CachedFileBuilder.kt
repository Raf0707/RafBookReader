/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.file

import androidx.compose.runtime.Immutable

@Immutable
data class CachedFileBuilder(
    val name: String?,
    val path: String?,
    val size: Long? = null,
    val lastModified: Long? = null,
    val isDirectory: Boolean? = null
)