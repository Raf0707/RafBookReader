package com.byteflipper.everbook.presentation.screens.start.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.byteflipper.everbook.R
import com.byteflipper.everbook.domain.model.ButtonItem
import com.byteflipper.everbook.presentation.core.constants.Constants
import com.byteflipper.everbook.presentation.core.constants.provideLanguages
import com.byteflipper.everbook.presentation.data.MainViewModel
import com.byteflipper.everbook.presentation.screens.start.components.permissions.startPermissionsScreen
import com.byteflipper.everbook.presentation.screens.start.data.StartEvent
import com.byteflipper.everbook.presentation.screens.start.data.StartNavigationScreen
import com.byteflipper.everbook.presentation.screens.start.data.StartViewModel

/**
 * Start Settings.
 *
 * @param storagePermissionState Storage [PermissionState].
 * @param notificationsPermissionState Notifications [PermissionState].
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartSettings(
    storagePermissionState: PermissionState,
    notificationsPermissionState: PermissionState
) {
    val state = StartViewModel.getState()
    val mainState = MainViewModel.getState()
    val onEvent = StartViewModel.getEvent()
    val onMainEvent = MainViewModel.getEvent()
    val activity = LocalContext.current as ComponentActivity

    val languages = remember(mainState.value.language) {
        Constants.provideLanguages()
            .sortedBy { it.second }
            .map {
                ButtonItem(
                    id = it.first,
                    title = it.second,
                    textStyle = TextStyle(),
                    selected = it.first == mainState.value.language
                )
            }
    }

    StartNavigationTransition(
        modifier = Modifier.padding(horizontal = 18.dp),
        visible = !state.value.isDone,
        bottomBar = {
            Column {
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 18.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(100),
                    onClick = { onEvent(StartEvent.OnGoForward) },
                    enabled = state.value.storagePermissionGranted ||
                            state.value.currentScreen != StartNavigationScreen.PERMISSIONS_THIRD
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Icon(
            imageVector = Icons.Default.GolfCourse,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.start_welcome),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.start_welcome_desc),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large)
                .background(
                    MaterialTheme.colorScheme.surfaceContainerLow,
                    MaterialTheme.shapes.large
                ),
        ) {
            StartNavigationScreenItem(
                screen = StartNavigationScreen.LANGUAGE_FIRST
            ) {
                startLanguageScreen(onMainEvent = onMainEvent, languages = languages)
            }

            StartNavigationScreenItem(
                screen = StartNavigationScreen.APPEARANCE_SECOND
            ) {
                startAppearanceScreen()
            }

            StartNavigationScreenItem(
                screen = StartNavigationScreen.PERMISSIONS_THIRD
            ) {
                startPermissionsScreen(
                    state = state,
                    onGrantStoragePermission = {
                        onEvent(
                            StartEvent.OnStoragePermissionRequest(
                                activity,
                                storagePermissionState
                            )
                        )
                    },
                    onGrantNotificationsPermission = {
                        onEvent(
                            StartEvent.OnNotificationsPermissionRequest(
                                activity,
                                notificationsPermissionState
                            )
                        )
                    }
                )
            }
        }
    }
}