/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.dialog.Dialog
import raf.console.chitalka.presentation.core.components.dialog.DialogCenter

@Composable
fun CategoryDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    DialogCenter(
        title = stringResource(id = R.string.delete_category),
        icon = Icons.Outlined.DeleteOutline,
        description = stringResource(id = R.string.delete_category_confirm),
        actionEnabled = true,
        onAction = onConfirm,
        onDismiss = onDismiss,
        withContent = false
    )
}