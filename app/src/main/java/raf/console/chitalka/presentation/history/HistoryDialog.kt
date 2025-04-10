/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.history

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.util.Dialog
import raf.console.chitalka.ui.history.HistoryEvent
import raf.console.chitalka.ui.history.HistoryScreen

@Composable
fun HistoryDialog(
    dialog: Dialog?,
    actionDeleteWholeHistoryDialog: (HistoryEvent.OnActionDeleteWholeHistoryDialog) -> Unit,
    dismissDialog: (HistoryEvent.OnDismissDialog) -> Unit
) {
    when (dialog) {
        HistoryScreen.DELETE_WHOLE_HISTORY_DIALOG -> {
            HistoryDeleteWholeHistoryDialog(
                actionDeleteWholeHistoryDialog = actionDeleteWholeHistoryDialog,
                dismissDialog = dismissDialog
            )
        }
    }
}