/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.crash

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
//import raf.console.chitalka.presentation.core.components.common.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.presentation.core.components.common.StyledText

@Composable
fun CrashContent(
    crashLog: String?,
    copy: () -> Unit,
    report: () -> Unit
) {
    CrashScaffold(
        copy = copy,
        report = report
    ) {
        CrashLayout {
            if (!crashLog.isNullOrBlank()) {
                item {
                    SelectionContainer {
                        StyledText(
                            text = crashLog,
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    /*SelectionContainer(
                        onCopyRequested = copy,
                        onShareRequested = {}, // или передайте нужную реализацию
                        onWebSearchRequested = {},
                        onTranslateRequested = {},
                        onDictionaryRequested = {}
                    ) { toolbarHidden ->
                        StyledText(
                            text = crashLog,
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }*/
                }
            }
        }
    }
}