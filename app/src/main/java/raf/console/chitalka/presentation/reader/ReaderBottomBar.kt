/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.reader.Checkpoint
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.translation.BookTranslationStatus
import raf.console.chitalka.domain.util.Direction
import raf.console.chitalka.presentation.core.components.common.IconButton
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.util.noRippleClickable
import raf.console.chitalka.ui.reader.ReaderEvent
import raf.console.chitalka.ui.theme.readerBarsColor
import raf.console.chitalka.ui.theme.HorizontalExpandingTransition

@Composable
fun ReaderBottomBar(
    book: Book,
    progress: String,
    text: List<ReaderText>,
    listState: LazyListState,
    lockMenu: Boolean,
    checkpoint: Checkpoint,
    bottomBarPadding: Dp,
    isBookTranslationRunning: Boolean,
    bookTranslationStatus: BookTranslationStatus,
    bookTranslationProgress: Float,
    bookTranslationMessage: String?,
    bookTranslationElapsedSeconds: Long,
    bookTranslationProgressInBottomBar: Boolean,
    bookTranslationPartialNotice: Boolean,
    cancelBookTranslation: (ReaderEvent.OnCancelBookTranslation) -> Unit,
    restoreCheckpoint: (ReaderEvent.OnRestoreCheckpoint) -> Unit,
    scroll: (ReaderEvent.OnScroll) -> Unit,
    changeProgress: (ReaderEvent.OnChangeProgress) -> Unit
) {
    val firstVisibleItemIndex = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }
    val arrowDirection = remember(checkpoint.index, firstVisibleItemIndex) {
        derivedStateOf {
            val checkpointIndex = checkpoint.index
            val index = firstVisibleItemIndex.value

            when {
                checkpointIndex > index -> Direction.END
                checkpointIndex < index -> Direction.START
                else -> Direction.NEUTRAL
            }
        }
    }
    val checkpointProgress = remember(checkpoint.index, text.lastIndex) {
        derivedStateOf {
            (checkpoint.index / text.lastIndex.toFloat()) * 0.987f
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.readerBarsColor)
            .noRippleClickable(onClick = {})
            .navigationBarsPadding()
            .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(16.dp))

        StyledText(
            text = progress,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(Modifier.height(6.dp))

        BookTranslationBottomStatus(
            isRunning = isBookTranslationRunning,
            status = bookTranslationStatus,
            progress = bookTranslationProgress,
            message = bookTranslationMessage,
            elapsedSeconds = bookTranslationElapsedSeconds,
            showProgress = bookTranslationProgressInBottomBar,
            showPartialNotice = bookTranslationPartialNotice,
            onCancel = {
                cancelBookTranslation(ReaderEvent.OnCancelBookTranslation)
            }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalExpandingTransition(
                visible = arrowDirection.value == Direction.START,
                startDirection = true
            ) {
                IconButton(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = R.string.checkpoint_back_content_desc,
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    disableOnClick = false
                ) {
                    restoreCheckpoint(ReaderEvent.OnRestoreCheckpoint)
                }
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                ReaderBottomBarSlider(
                    book = book,
                    lockMenu = lockMenu,
                    listState = listState,
                    scroll = scroll,
                    changeProgress = changeProgress
                )

                if (arrowDirection.value != Direction.NEUTRAL) {
                    ReaderBottomBarSliderIndicator(progress = checkpointProgress.value)
                }
            }

            HorizontalExpandingTransition(
                visible = arrowDirection.value == Direction.END,
                startDirection = false
            ) {
                IconButton(
                    icon = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = R.string.checkpoint_forward_content_desc,
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    disableOnClick = false
                ) {
                    restoreCheckpoint(ReaderEvent.OnRestoreCheckpoint)
                }
            }
        }

        Spacer(Modifier.height(8.dp + bottomBarPadding))
    }
}

@Composable
private fun BookTranslationBottomStatus(
    isRunning: Boolean,
    status: BookTranslationStatus,
    progress: Float,
    message: String?,
    elapsedSeconds: Long,
    showProgress: Boolean,
    showPartialNotice: Boolean,
    onCancel: () -> Unit
) {
    if (!isRunning) return

    val normalizedProgress = progress.coerceIn(0f, 1f)
    val displayMessage = message ?: when (status) {
        BookTranslationStatus.CheckingModels -> "Проверка языковых моделей"
        BookTranslationStatus.DownloadingModel -> "Загрузка языковой модели"
        BookTranslationStatus.Translating -> "Перевод книги"
        BookTranslationStatus.Idle -> "Подготовка перевода"
    }
    val detail = when (status) {
        BookTranslationStatus.Translating -> "${(normalizedProgress * 100).toInt()}%"
        BookTranslationStatus.DownloadingModel -> "${elapsedSeconds} с"
        BookTranslationStatus.CheckingModels -> "${elapsedSeconds} с"
        BookTranslationStatus.Idle -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StyledText(
                text = if (detail.isBlank()) displayMessage else "$displayMessage · $detail",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1
            )

            TextButton(
                modifier = Modifier.padding(start = 8.dp),
                onClick = onCancel
            ) {
                StyledText(
                    text = "Отмена",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 1
                )
            }
        }

        if (showProgress) {
            if (
                status == BookTranslationStatus.CheckingModels ||
                status == BookTranslationStatus.DownloadingModel
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                LinearProgressIndicator(
                    progress = { normalizedProgress },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (
            showPartialNotice &&
            status == BookTranslationStatus.Translating &&
            normalizedProgress < 1f
        ) {
            Spacer(Modifier.height(4.dp))
            StyledText(
                text = "Часть текста ещё оригинальная",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1
            )
        }

        Spacer(Modifier.height(4.dp))
    }
}
