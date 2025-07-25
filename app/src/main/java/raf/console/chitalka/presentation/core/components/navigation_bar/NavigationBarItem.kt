/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.components.navigation_bar

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.navigator.NavigatorItem
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.components.common.Tooltip

@Composable
fun RowScope.NavigationBarItem(
    modifier: Modifier = Modifier,
    item: NavigatorItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        label = {
            Tooltip(
                text = stringResource(id = item.tooltip),
                padding = 64.dp
            ) {
                StyledText(
                    text = stringResource(id = item.title),
                    maxLines = 1
                )
            }
        },
        selected = isSelected,
        onClick = { onClick() },
        icon = {
            Tooltip(
                text = stringResource(id = item.tooltip),
                padding = 32.dp
            ) {
                Crossfade(
                    targetState = isSelected,
                    animationSpec = tween(150),
                    label = ""
                ) {
                    if (it) {
                        Icon(
                            painter = painterResource(id = item.selectedIcon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = item.unselectedIcon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}