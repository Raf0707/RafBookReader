/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.browse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.NullableBook
import raf.console.chitalka.domain.library.book.SelectableNullableBook
import raf.console.chitalka.presentation.core.components.common.CircularCheckbox
import raf.console.chitalka.presentation.core.components.common.StyledText

@Composable
fun BrowseAddDialogItem(result: SelectableNullableBook, onClick: (Boolean) -> Unit) {
    if (result.data is NullableBook.NotNull) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(true)
                }
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                StyledText(
                    text = result.data.bookWithCover!!.book.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1
                )
                StyledText(
                    text = result.data.bookWithCover.book.author.asString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                )
            }
            Row {
                Spacer(modifier = Modifier.width(24.dp))
                CircularCheckbox(
                    selected = result.selected,
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            }
        }
    } else {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(false)
                }
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(id = R.string.error_content_desc),
                modifier = Modifier.size(26.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(8.dp))
            StyledText(
                text = result.data.fileName!!,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                maxLines = 2
            )
        }
    }
}