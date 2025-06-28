/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.dialog.Dialog
import raf.console.chitalka.ui.history.HistoryEvent

@Composable
fun HistoryDeleteWholeHistoryDialog(
    actionDeleteWholeHistoryDialog: (HistoryEvent.OnActionDeleteWholeHistoryDialog) -> Unit,
    dismissDialog: (HistoryEvent.OnDismissDialog) -> Unit
) {
    val context = LocalContext.current
    Dialog(
        title = stringResource(id = R.string.delete_history),
        icon = Icons.Outlined.DeleteOutline,
        description = stringResource(id = R.string.delete_history_description),
        actionEnabled = true,
        onDismiss = {
            dismissDialog(HistoryEvent.OnDismissDialog)
        },
        onAction = {
            actionDeleteWholeHistoryDialog(
                HistoryEvent.OnActionDeleteWholeHistoryDialog(
                    context = context
                )
            )
        },
        withContent = false
    )
}