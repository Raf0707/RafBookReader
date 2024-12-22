package com.byteflipper.everbook.presentation.core.components.navigation_bar

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.byteflipper.everbook.domain.util.Route
import com.byteflipper.everbook.presentation.core.constants.Constants
import com.byteflipper.everbook.presentation.core.constants.provideNavigationItems
import com.byteflipper.everbook.presentation.core.navigation.LocalNavigatorInstance

/**
 * Navigation bar, uses default [NavigationBar].
 */
@Composable
fun NavigationBar() {
    var currentScreen: Route? by remember { mutableStateOf(null) }
    val navigator = LocalNavigatorInstance.current

    LaunchedEffect(Unit) {
        navigator.currentScreen.collect { route ->
            if (
                Constants.provideNavigationItems().any {
                    navigator.run { it.screen.getRoute() } == route
                }
            ) {
                currentScreen = route
            }
        }
    }

    NavigationBar {
        Constants.provideNavigationItems().forEach {
            NavigationBarItem(
                item = it,
                isSelected = currentScreen == navigator.run { it.screen.getRoute() }
            ) {
                navigator.navigate(it.screen, false)
            }
        }
    }
}
