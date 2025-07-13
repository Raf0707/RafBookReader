package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.components.modal_drawer.ModalDrawer
import raf.console.chitalka.presentation.core.components.modal_drawer.ModalDrawerSelectableItem
import raf.console.chitalka.presentation.core.components.modal_drawer.ModalDrawerTitleItem
import raf.console.chitalka.ui.reader.ReaderEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReaderNotesBookmarksDrawer(
    show: Boolean,
    bookmarks: List<Bookmark>,
    notes: List<Note>,
    dismissDrawer: (ReaderEvent.OnDismissDrawer) -> Unit,
    onEvent: (ReaderEvent) -> Unit,
    listState: LazyListState
) {
    ModalDrawer(
        show = show,
        onDismissRequest = { dismissDrawer(ReaderEvent.OnDismissDrawer) },
        header = {
            ModalDrawerTitleItem(
                title = "Заметки и закладки"
            )
        }
    ) {
        if (bookmarks.isEmpty() && notes.isEmpty()) {
            item {
                StyledText(
                    text = "Нет закладок",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        if (bookmarks.isNotEmpty()) {
            item {
                ModalDrawerTitleItem(title = "Закладки")
            }

            items(bookmarks.size) { index ->
                val bookmark = bookmarks[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Переход к закладке и выделение текста
                            bookmark.label?.let {
                                ReaderEvent.OnScrollToBookmark(
                                    chapterIndex = bookmark.chapterIndex.toInt(),
                                    offset = bookmark.offset,
                                    text = it // используем для выделения
                                )
                            }?.let { onEvent(it) }
                            dismissDrawer(ReaderEvent.OnDismissDrawer)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StyledText(
                        text = bookmark.label.toString(),//?.take(64) ?: "",
                        modifier = Modifier.weight(1f),
                        //maxLines = 2
                    )
                    IconButton(onClick = {
                        // Удаление закладки
                        onEvent(ReaderEvent.OnDeleteBookmark(bookmark))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить закладку"
                        )
                    }
                }

            }
        }

        /*if (notes.isNotEmpty()) {
            item {
                ModalDrawerTitleItem(title = "Заметки")
            }

            items(notes.size) { index ->
                val note = notes[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(ReaderEvent.OnJumpToNote(note))
                            dismissDrawer(ReaderEvent.OnDismissDrawer)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StyledText(
                        text = note.content.take(64),
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )
                    IconButton(onClick = {
                        onEvent(ReaderEvent.OnDeleteNote(note))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить заметку"
                        )
                    }
                }
            }
        }*/
    }
}





