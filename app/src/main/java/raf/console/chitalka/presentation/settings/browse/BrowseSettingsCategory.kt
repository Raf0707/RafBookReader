/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.settings.browse

import androidx.compose.foundation.lazy.LazyListScope
import raf.console.chitalka.presentation.settings.browse.display.BrowseDisplaySubcategory
import raf.console.chitalka.presentation.settings.browse.filter.BrowseFilterSubcategory
import raf.console.chitalka.presentation.settings.browse.scan.BrowseScanSubcategory
import raf.console.chitalka.presentation.settings.browse.sort.BrowseSortSubcategory

fun LazyListScope.BrowseSettingsCategory() {
    BrowseScanSubcategory()
    BrowseDisplaySubcategory()
    BrowseFilterSubcategory()
    BrowseSortSubcategory(
        showDivider = false
    )
}