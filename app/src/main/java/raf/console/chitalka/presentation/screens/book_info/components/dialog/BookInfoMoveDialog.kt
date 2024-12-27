package raf.console.chitalka.presentation.screens.book_info.components.dialog

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DriveFileMove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.domain.model.Category
import raf.console.chitalka.presentation.core.components.dialog.DialogWithLazyColumn
import raf.console.chitalka.presentation.core.components.dialog.SelectableDialogItem
import raf.console.chitalka.presentation.core.navigation.LocalNavigator
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.presentation.screens.book_info.data.BookInfoEvent
import raf.console.chitalka.presentation.screens.book_info.data.BookInfoViewModel
import raf.console.chitalka.presentation.screens.history.data.HistoryEvent
import raf.console.chitalka.presentation.screens.history.data.HistoryViewModel
import raf.console.chitalka.presentation.screens.library.data.LibraryEvent
import raf.console.chitalka.presentation.screens.library.data.LibraryViewModel

/**
 * Move dialog.
 * Moves current book to the selected category.
 */
@Composable
fun BookInfoMoveDialog() {
    val state = BookInfoViewModel.getState()
    val onEvent = BookInfoViewModel.getEvent()
    val onLibraryEvent = LibraryViewModel.getEvent()
    val onHistoryEvent = HistoryViewModel.getEvent()
    val onNavigate = LocalNavigator.current
    val context = LocalContext.current

    val categories = remember {
        Category.entries.filter { state.value.book.category != it }
    }

    LaunchedEffect(Unit) {
        onEvent(BookInfoEvent.OnSelectCategory(categories[0]))
    }

    DialogWithLazyColumn(
        title = stringResource(id = R.string.move_book),
        imageVectorIcon = Icons.AutoMirrored.Outlined.DriveFileMove,
        description = stringResource(
            id = R.string.move_book_description
        ),
        actionText = stringResource(id = R.string.move),
        isActionEnabled = true,
        onDismiss = { onEvent(BookInfoEvent.OnShowHideMoveDialog) },
        onAction = {
            onEvent(
                BookInfoEvent.OnMoveBook(
                    refreshList = {
                        onLibraryEvent(LibraryEvent.OnUpdateBook(it))
                        onHistoryEvent(HistoryEvent.OnUpdateBook(it))
                    },
                    updatePage = {
                        onLibraryEvent(LibraryEvent.OnUpdateCurrentPage(it))
                    },
                    onNavigate = onNavigate
                )
            )
            onEvent(BookInfoEvent.OnShowHideMoreBottomSheet(false))
            context.getString(R.string.book_moved)
                .showToast(context = context)
        },
        withDivider = false,
        items = {
            items(categories, key = { it.name }) {
                val category = when (it) {
                    Category.READING -> stringResource(id = R.string.reading_tab)
                    Category.ALREADY_READ -> stringResource(id = R.string.already_read_tab)
                    Category.PLANNING -> stringResource(id = R.string.planning_tab)
                    Category.DROPPED -> stringResource(id = R.string.dropped_tab)
                }

                SelectableDialogItem(
                    selected = it == state.value.selectedCategory,
                    title = category
                ) {
                    onEvent(BookInfoEvent.OnSelectCategory(it))
                }
            }
        }
    )
}