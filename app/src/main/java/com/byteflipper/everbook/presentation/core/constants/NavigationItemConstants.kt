package com.byteflipper.everbook.presentation.core.constants

import com.byteflipper.everbook.R
import com.byteflipper.everbook.domain.model.NavigationItem
import com.byteflipper.everbook.presentation.core.navigation.Screen

fun Constants.provideNavigationItems() = listOf(
    NavigationItem(
        screen = Screen.Library,
        title = R.string.library_screen,
        tooltip = R.string.library_content_desc,
        selectedIcon = R.drawable.library_screen_filled,
        unselectedIcon = R.drawable.library_screen_outlined
    ),
    NavigationItem(
        screen = Screen.History,
        title = R.string.history_screen,
        tooltip = R.string.history_content_desc,
        selectedIcon = R.drawable.history_screen_filled,
        unselectedIcon = R.drawable.history_screen_outlined
    ),
    NavigationItem(
        screen = Screen.Browse,
        title = R.string.browse_screen,
        tooltip = R.string.browse_content_desc,
        selectedIcon = R.drawable.browse_screen_filled,
        unselectedIcon = R.drawable.browse_screen_outlined
    )
)