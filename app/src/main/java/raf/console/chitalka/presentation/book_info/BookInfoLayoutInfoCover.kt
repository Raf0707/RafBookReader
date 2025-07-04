/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.common.AsyncCoverImage
import raf.console.chitalka.presentation.core.util.noRippleClickable
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoLayoutInfoCover(
    book: Book,
    showChangeCoverBottomSheet: (BookInfoEvent.OnShowChangeCoverBottomSheet) -> Unit
) {
    val height = remember { 220.dp }
    val width = remember(height) { height * 0.66f }

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .noRippleClickable(
                onClick = {},
                onLongClick = {
                    showChangeCoverBottomSheet(BookInfoEvent.OnShowChangeCoverBottomSheet)
                }
            )
    ) {
        if (book.coverImage != null) {
            AsyncCoverImage(
                uri = book.coverImage,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = stringResource(id = R.string.cover_image_not_found_content_desc),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f),
                tint = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        }
    }
}