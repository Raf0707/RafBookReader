package raf.console.chitalka.presentation.screens.settings.nested.general.components.settings

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.dialog.DialogWithContent
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel
import raf.console.chitalka.presentation.screens.settings.data.SettingsEvent
import raf.console.chitalka.presentation.screens.settings.data.SettingsViewModel

/**
 * Check For Updates setting.
 * If true, checks for the update on app start.
 */
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("InlinedApi")
@Composable
fun CheckForUpdatesSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()
    val onSettingsEvent = SettingsViewModel.getEvent()
    val activity = LocalContext.current as ComponentActivity

    val notificationsPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val showConfirmation = remember { mutableStateOf(false) }

    if (showConfirmation.value) {
        DialogWithContent(
            title = stringResource(id = R.string.enable_check_for_updates),
            description = stringResource(id = R.string.enable_check_for_updates_description),
            actionText = stringResource(id = R.string.enable),
            imageVectorIcon = Icons.Default.Security,
            isActionEnabled = true,
            onDismiss = { showConfirmation.value = false },
            onAction = {
                onMainEvent(
                    MainEvent.OnChangeCheckForUpdates(
                        true
                    )
                )
                showConfirmation.value = false
            },
            withDivider = false
        )
    }

    SwitchWithTitle(
        selected = state.value.checkForUpdates,
        title = stringResource(id = R.string.check_for_updates_option),
        description = stringResource(id = R.string.check_for_updates_option_desc)
    ) {
        onSettingsEvent(
            SettingsEvent.OnGeneralChangeCheckForUpdates(
                enable = !state.value.checkForUpdates,
                activity = activity,
                notificationsPermissionState = notificationsPermissionState,
                onChangeCheckForUpdates = {
                    when (it) {
                        true -> showConfirmation.value = true
                        false -> onMainEvent(MainEvent.OnChangeCheckForUpdates(false))
                    }
                }
            )
        )
    }
}