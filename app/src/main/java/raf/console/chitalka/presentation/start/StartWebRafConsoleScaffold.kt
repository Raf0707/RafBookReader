package raf.console.chitalka.presentation.start

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.ui.about.AboutEvent

@Composable
fun StartWebRafConsoleScaffold(
    navigateForward: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            StartSettingsBottomBar(
                navigateForward = navigateForward
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        content(paddingValues)
    }
}