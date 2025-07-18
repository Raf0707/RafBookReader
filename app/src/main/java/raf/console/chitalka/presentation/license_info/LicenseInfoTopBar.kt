/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.license_info

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mikepenz.aboutlibraries.entity.Library
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.IconButton
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.navigator.NavigatorBackIconButton
import raf.console.chitalka.ui.about.AboutEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseInfoTopBar(
    library: Library,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToBrowserPage: (AboutEvent.OnNavigateToBrowserPage) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    LargeTopAppBar(
        title = {
            StyledText(
                text = library.name,
                maxLines = 1
            )
        },
        navigationIcon = {
            NavigatorBackIconButton(navigateBack = navigateBack)
        },
        actions = {
            if (library.website?.isNotBlank() == true) {
                IconButton(
                    icon = Icons.Outlined.Language,
                    contentDescription = R.string.open_in_web_content_desc,
                    disableOnClick = false
                ) {
                    navigateToBrowserPage(
                        AboutEvent.OnNavigateToBrowserPage(
                            page = library.website!!,
                            context = context
                        )
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    )
}