package raf.console.chitalka.presentation.screens.browse.components

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.dialog.DialogWithContent
import raf.console.chitalka.presentation.screens.browse.data.BrowseEvent
import raf.console.chitalka.presentation.screens.browse.data.BrowseViewModel

/**
 * Storage permission dialog.
 *
 * @param permissionState Storage [PermissionState].
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BrowseStoragePermissionDialog(
    permissionState: PermissionState
) {
    val activity = LocalContext.current as ComponentActivity
    val onEvent = BrowseViewModel.getEvent()

    DialogWithContent(
        title = stringResource(id = R.string.storage_permission),
        description = stringResource(id = R.string.storage_permission_description),
        actionText = stringResource(id = R.string.grant),
        imageVectorIcon = Icons.Default.SdStorage,
        onDismiss = {
            onEvent(
                BrowseEvent.OnStoragePermissionDismiss(
                    permissionState = permissionState
                )
            )
        },
        isActionEnabled = true,
        withDivider = false,
        disableOnClick = false,
        onAction = {
            onEvent(
                BrowseEvent.OnStoragePermissionRequest(
                    activity = activity,
                    storagePermissionState = permissionState
                )
            )
        }
    )
}