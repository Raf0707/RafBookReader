/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.components.common

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import raf.console.chitalka.R

/**
 * Custom Cover Image. Has smooth appearing.
 */
@Composable
fun AsyncCoverImage(
    uri: Uri,
    animationDurationMillis: Int = 100,
    contentDescription: String? = stringResource(id = R.string.cover_image_content_desc),
    modifier: Modifier,
    alpha: Float = 1f,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(animationDurationMillis)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        alpha = alpha,
        contentScale = ContentScale.Crop
    )
}