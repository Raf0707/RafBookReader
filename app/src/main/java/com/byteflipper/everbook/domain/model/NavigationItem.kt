package com.byteflipper.everbook.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.byteflipper.everbook.presentation.core.navigation.Screen

@Immutable
data class NavigationItem(
    val screen: Screen,
    @StringRes val title: Int,
    @StringRes val tooltip: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
)