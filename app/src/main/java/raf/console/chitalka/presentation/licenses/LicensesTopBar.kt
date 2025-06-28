/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.licenses

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.navigator.NavigatorBackIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicensesTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navigateBack: () -> Unit
) {
    LargeTopAppBar(
        title = {
            StyledText(stringResource(id = R.string.licenses_option))
        },
        navigationIcon = {
            NavigatorBackIconButton(navigateBack = navigateBack)
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    )
}