/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.about

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.about.Badge
import raf.console.chitalka.presentation.core.components.common.IconButton

@Composable
fun AboutBadgeItem(
    badge: Badge,
    onClick: () -> Unit
) {
    if (badge.imageVector == null && badge.drawable != null) {
        IconButton(
            modifier = Modifier.size(22.dp),
            icon = badge.drawable,
            contentDescription = badge.contentDescription,
            disableOnClick = false,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            onClick()
        }
    } else if (badge.imageVector != null && badge.drawable == null) {
        IconButton(
            modifier = Modifier.size(22.dp),
            icon = badge.imageVector,
            contentDescription = badge.contentDescription,
            disableOnClick = false,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            onClick()
        }
    }
}