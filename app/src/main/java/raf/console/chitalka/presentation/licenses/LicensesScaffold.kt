/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.licenses

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.mikepenz.aboutlibraries.entity.Library

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicensesScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    licenses: List<Library>,
    listState: LazyListState,
    navigateToLicenseInfo: (Library) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            LicensesTopBar(
                scrollBehavior = scrollBehavior,
                navigateBack = navigateBack
            )
        }
    ) { paddingValues ->
        LicensesLayout(
            licenses = licenses,
            paddingValues = paddingValues,
            listState = listState,
            navigateToLicenseInfo = navigateToLicenseInfo
        )
    }
}