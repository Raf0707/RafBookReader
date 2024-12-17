package com.byteflipper.book_story.presentation.screens.book_info.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.dialog.DialogWithContent
import com.byteflipper.book_story.presentation.core.navigation.LocalNavigator
import com.byteflipper.book_story.presentation.core.util.showToast
import com.byteflipper.book_story.presentation.screens.book_info.data.BookInfoEvent
import com.byteflipper.book_story.presentation.screens.book_info.data.BookInfoViewModel
import com.byteflipper.book_story.presentation.screens.browse.data.BrowseEvent
import com.byteflipper.book_story.presentation.screens.browse.data.BrowseViewModel
import com.byteflipper.book_story.presentation.screens.history.data.HistoryEvent
import com.byteflipper.book_story.presentation.screens.history.data.HistoryViewModel
import com.byteflipper.book_story.presentation.screens.library.data.LibraryEvent
import com.byteflipper.book_story.presentation.screens.library.data.LibraryViewModel

/**
 * Delete dialog. Deletes current book.
 */
@Composable
fun BookInfoDeleteDialog() {
    val onEvent = BookInfoViewModel.getEvent()
    val onLibraryEvent = LibraryViewModel.getEvent()
    val onHistoryEvent = HistoryViewModel.getEvent()
    val onBrowseEvent = BrowseViewModel.getEvent()
    val onNavigate = LocalNavigator.current
    val context = LocalContext.current

    DialogWithContent(
        title = stringResource(id = R.string.delete_book),
        imageVectorIcon = Icons.Outlined.DeleteOutline,
        description = stringResource(
            id = R.string.delete_book_description
        ),
        actionText = stringResource(id = R.string.delete),
        onDismiss = { onEvent(BookInfoEvent.OnShowHideDeleteDialog) },
        withDivider = false,
        isActionEnabled = true,
        onAction = {
            onEvent(
                BookInfoEvent.OnDeleteBook(
                    onNavigate = onNavigate,
                    refreshList = {
                        onLibraryEvent(LibraryEvent.OnLoadList)
                        onBrowseEvent(BrowseEvent.OnLoadList)
                        onHistoryEvent(HistoryEvent.OnLoadList)
                    }
                )
            )
            onEvent(BookInfoEvent.OnShowHideMoreBottomSheet(false))
            context.getString(R.string.book_deleted)
                .showToast(context = context)
        }
    )
}