package com.byteflipper.everbook.presentation.screens.help.data

import androidx.compose.runtime.Immutable
import com.byteflipper.everbook.presentation.core.navigation.Screen

@Immutable
sealed class HelpEvent {
    data class OnInit(
        val screen: Screen.Help
    ) : HelpEvent()
}