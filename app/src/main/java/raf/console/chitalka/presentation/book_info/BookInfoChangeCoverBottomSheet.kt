/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.modal_bottom_sheet.ModalBottomSheet
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoChangeCoverBottomSheet(
    book: Book,
    canResetCover: Boolean,
    changeCover: (BookInfoEvent.OnChangeCover) -> Unit,
    resetCover: (BookInfoEvent.OnResetCover) -> Unit,
    deleteCover: (BookInfoEvent.OnDeleteCover) -> Unit,
    checkCoverReset: (BookInfoEvent.OnCheckCoverReset) -> Unit,
    dismissBottomSheet: (BookInfoEvent.OnDismissBottomSheet) -> Unit
) {
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                changeCover(
                    BookInfoEvent.OnChangeCover(
                        uri = uri,
                        context = context
                    )
                )
            }
        }
    )

    LaunchedEffect(Unit) {
        checkCoverReset(BookInfoEvent.OnCheckCoverReset)
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = {
            dismissBottomSheet(BookInfoEvent.OnDismissBottomSheet)
        },
        sheetGesturesEnabled = true
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (canResetCover) {
                item {
                    BookInfoChangeCoverBottomSheetItem(
                        icon = Icons.Default.Restore,
                        text = stringResource(id = R.string.reset_cover),
                        description = stringResource(id = R.string.reset_cover_desc)
                    ) {
                        resetCover(
                            BookInfoEvent.OnResetCover(
                                context = context
                            )
                        )
                    }
                }
            }

            item {
                BookInfoChangeCoverBottomSheetItem(
                    icon = Icons.Default.ImageSearch,
                    text = stringResource(id = R.string.change_cover),
                    description = stringResource(id = R.string.change_cover_desc)
                ) {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            }

            if (book.coverImage != null) {
                item {
                    BookInfoChangeCoverBottomSheetItem(
                        icon = Icons.Default.HideImage,
                        text = stringResource(id = R.string.delete_cover),
                        description = stringResource(id = R.string.delete_cover_desc)
                    ) {
                        deleteCover(
                            BookInfoEvent.OnDeleteCover(
                                context = context
                            )
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}