/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.navigator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.IconButton

@Composable
fun NavigatorBackIconButton(
    enabled: Boolean = true,
    navigateBack: () -> Unit,
) {
    IconButton(
        icon = Icons.AutoMirrored.Outlined.ArrowBack,
        contentDescription = R.string.go_back_content_desc,
        disableOnClick = true,
        enabled = enabled
    ) {
        navigateBack()
    }
}