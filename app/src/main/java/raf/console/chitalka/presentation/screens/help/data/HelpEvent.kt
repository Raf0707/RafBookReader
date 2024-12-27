package raf.console.chitalka.presentation.screens.help.data

import androidx.compose.runtime.Immutable
import raf.console.chitalka.presentation.core.navigation.Screen

@Immutable
sealed class HelpEvent {
    data class OnInit(
        val screen: Screen.Help
    ) : HelpEvent()
}