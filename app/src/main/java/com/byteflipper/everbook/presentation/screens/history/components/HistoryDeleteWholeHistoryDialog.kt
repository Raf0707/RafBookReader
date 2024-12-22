package com.byteflipper.everbook.presentation.screens.history.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.dialog.DialogWithContent
import com.byteflipper.everbook.presentation.core.util.showToast
import com.byteflipper.everbook.presentation.screens.history.data.HistoryEvent
import com.byteflipper.everbook.presentation.screens.history.data.HistoryViewModel
import com.byteflipper.everbook.presentation.screens.library.data.LibraryEvent
import com.byteflipper.everbook.presentation.screens.library.data.LibraryViewModel

/**
 * Delete whole history dialog.
 */
@Composable
fun HistoryDeleteWholeHistoryDialog() {
    val context = LocalContext.current
    val onEvent = HistoryViewModel.getEvent()
    val onLibraryEvent = LibraryViewModel.getEvent()

    DialogWithContent(
        title = stringResource(id = R.string.delete_history),
        imageVectorIcon = Icons.Outlined.DeleteOutline,
        description = stringResource(
            id = R.string.delete_history_description
        ),
        actionText = stringResource(id = R.string.delete),
        onDismiss = { onEvent(HistoryEvent.OnShowHideDeleteWholeHistoryDialog) },
        withDivider = false,
        isActionEnabled = true,
        onAction = {
            onEvent(
                HistoryEvent.OnDeleteWholeHistory {
                    onLibraryEvent(LibraryEvent.OnLoadList)
                }
            )
            context.getString(R.string.history_deleted)
                .showToast(context = context)
        }
    )
}