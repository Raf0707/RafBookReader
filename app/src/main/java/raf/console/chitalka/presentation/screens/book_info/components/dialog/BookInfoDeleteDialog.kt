package raf.console.chitalka.presentation.screens.book_info.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.dialog.DialogWithContent
import raf.console.chitalka.presentation.core.navigation.LocalNavigator
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.presentation.screens.book_info.data.BookInfoEvent
import raf.console.chitalka.presentation.screens.book_info.data.BookInfoViewModel
import raf.console.chitalka.presentation.screens.browse.data.BrowseEvent
import raf.console.chitalka.presentation.screens.browse.data.BrowseViewModel
import raf.console.chitalka.presentation.screens.history.data.HistoryEvent
import raf.console.chitalka.presentation.screens.history.data.HistoryViewModel
import raf.console.chitalka.presentation.screens.library.data.LibraryEvent
import raf.console.chitalka.presentation.screens.library.data.LibraryViewModel

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