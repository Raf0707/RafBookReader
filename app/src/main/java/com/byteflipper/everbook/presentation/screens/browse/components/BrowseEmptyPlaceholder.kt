package com.byteflipper.everbook.presentation.screens.browse.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.common.AnimatedVisibility
import com.byteflipper.everbook.presentation.core.components.placeholder.EmptyPlaceholder
import com.byteflipper.everbook.presentation.core.components.placeholder.ErrorPlaceholder
import com.byteflipper.everbook.presentation.core.navigation.LocalNavigator
import com.byteflipper.everbook.presentation.core.navigation.Screen
import com.byteflipper.everbook.presentation.screens.browse.data.BrowseEvent
import com.byteflipper.everbook.presentation.screens.browse.data.BrowseViewModel
import com.byteflipper.everbook.presentation.ui.Transitions

/**
 * Browse Empty Placeholder.
 * Shows error or empty message.
 *
 * @param isFilesEmpty Whether the list is empty.
 * @param storagePermissionState Storage [PermissionState].
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BoxScope.BrowseEmptyPlaceholder(
    isFilesEmpty: Boolean,
    storagePermissionState: PermissionState
) {
    val state = BrowseViewModel.getState()
    val onEvent = BrowseViewModel.getEvent()
    val onNavigate = LocalNavigator.current

    AnimatedVisibility(
        visible = state.value.isError,
        modifier = Modifier.align(Alignment.Center),
        enter = Transitions.DefaultTransitionIn,
        exit = Transitions.NoExitAnimation
    ) {
        ErrorPlaceholder(
            modifier = Modifier.align(Alignment.Center),
            errorMessage = stringResource(id = R.string.error_permission),
            icon = painterResource(id = R.drawable.error),
            actionTitle = stringResource(id = R.string.grant_permission)
        ) {
            onEvent(
                BrowseEvent.OnPermissionCheck(storagePermissionState)
            )
        }
    }

    AnimatedVisibility(
        visible = !state.value.isLoading
                && isFilesEmpty
                && !state.value.isError
                && !state.value.requestPermissionDialog
                && !state.value.isRefreshing,
        modifier = Modifier.align(Alignment.Center),
        enter = Transitions.DefaultTransitionIn,
        exit = Transitions.NoExitAnimation
    ) {
        EmptyPlaceholder(
            message = stringResource(id = R.string.browse_empty),
            icon = painterResource(id = R.drawable.empty_browse),
            actionTitle = stringResource(id = R.string.get_help),
            action = {
                onNavigate {
                    navigate(Screen.Help(false))
                }
            }
        )
    }
}