/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.licenses

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.mikepenz.aboutlibraries.entity.Library

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicensesContent(
    scrollBehavior: TopAppBarScrollBehavior,
    licenses: List<Library>,
    listState: LazyListState,
    navigateToLicenseInfo: (Library) -> Unit,
    navigateBack: () -> Unit
) {
    LicensesScaffold(
        scrollBehavior = scrollBehavior,
        licenses = licenses,
        listState = listState,
        navigateToLicenseInfo = navigateToLicenseInfo,
        navigateBack = navigateBack
    )
}