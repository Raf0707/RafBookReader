/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.StyledText

@Composable
fun StartSettingsBottomBar(
    navigateForward: () -> Unit,
    enabled: Boolean = true
) {
    Column {
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 8.dp)
                .padding(horizontal = 18.dp)
                .fillMaxWidth(),
            onClick = navigateForward,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            StyledText(text = stringResource(id = R.string.next))
        }
    }
}