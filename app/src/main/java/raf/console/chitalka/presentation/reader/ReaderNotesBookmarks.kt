package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
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
fun ReaderNotesBookmarks(
    bookmarks: List<Bookmark>,
    notes: List<Note>,
    onBookmarkClick: (Bookmark) -> Unit,
    onNoteClick: (Note) -> Unit,
    onNoteAdd: () -> Unit,
    onNoteEdit: (Note) -> Unit,
    onNoteDelete: (Note) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) } // 0 = Закладки, 1 = Заметки

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // ✅ Добавлены отступы
    ) {
        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Закладки", modifier = Modifier.padding(16.dp))
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Заметки", modifier = Modifier.padding(16.dp))
            }
        }

        // Облако
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Войдите, чтобы сохранить закладки и заметки в облаке",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Divider()

        // Списки или сообщение об отсутствии
        when (selectedTab) {
            0 -> {
                if (bookmarks.isEmpty()) {
                    Text("Нет закладок", modifier = Modifier.padding(16.dp))
                } else {
                    BookmarkList(bookmarks, onBookmarkClick)
                }
            }

            1 -> {
                NoteHeader(onNoteAdd)
                if (notes.isEmpty()) {
                    Text("Нет заметок", modifier = Modifier.padding(16.dp))
                } else {
                    NoteList(notes, onNoteClick, onNoteEdit, onNoteDelete)
                }
            }
        }
    }
}

@Composable
private fun NoteHeader(onNoteAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Ваши заметки",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Добавить",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onNoteAdd() }
        )
    }
}



@Composable
private fun BookmarkList(
    bookmarks: List<Bookmark>,
    onClick: (Bookmark) -> Unit
) {
    LazyColumn {
        items(bookmarks.size) { bookmark ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(bookmarks[bookmark]) }
                    .padding(16.dp)
            ) {
                Text(
                    text = bookmarks[bookmark].label?.takeIf { it.isNotBlank() } ?: "Без названия",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = bookmarks[bookmark].createdAt.toFormattedDateTime(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Глава ${bookmarks[bookmark].chapterIndex + 1}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Divider()
        }
    }
}

@Composable
private fun NoteList(
    notes: List<Note>,
    onClick: (Note) -> Unit,
    onEdit: (Note) -> Unit,
    onDelete: (Note) -> Unit
) {
    LazyColumn {
        items(notes.size) { i ->
            val note = notes[i]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(note) }
                    .padding(16.dp)
            ) {
                Text(text = note.content.take(100), fontWeight = FontWeight.Medium)
                Text(
                    text = note.createdAt.toFormattedDateTime(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Глава ${note.chapterIndex + 1}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row {
                    Text(
                        "Изменить",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { onEdit(note) },
                        color = Color.Blue,
                        fontSize = 14.sp
                    )
                    Text(
                        "Удалить",
                        modifier = Modifier.clickable { onDelete(note) },
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            }
            Divider()
        }
    }
}


@Composable
private fun NoteList1(
    notes: List<Note>,
    onClick: (Note) -> Unit
) {
    LazyColumn {
        items(notes.size) { note ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(notes[note]) }
                    .padding(16.dp)
            ) {
                Text(
                    text = notes[note].content.take(100),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = notes[note].createdAt.toFormattedDateTime(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Глава ${notes[note].chapterIndex + 1}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Divider()
        }
    }
}


fun Long.toFormattedDateTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}


@Composable
fun ReaderNotesBookmarksDrawer(
    show: Boolean,
    bookmarks: List<Bookmark>,
    notes: List<Note>,
    dismissDrawer: (ReaderEvent.OnDismissDrawer) -> Unit
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

            items(bookmarks.size) { bookmark ->
                ModalDrawerSelectableItem(
                    selected = false,
                    onClick = { dismissDrawer(ReaderEvent.OnDismissDrawer) }
                ) {
                    StyledText(
                        text = bookmarks[bookmark].label!!.take(64),
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )
                }
            }
        }

        if (notes.isNotEmpty()) {
            item {
                ModalDrawerTitleItem(title = "Заметки")
            }

            items(notes.size) { note ->
                ModalDrawerSelectableItem(
                    selected = false,
                    onClick = { dismissDrawer(ReaderEvent.OnDismissDrawer) }
                ) {
                    StyledText(
                        text = notes[note].content.take(64),
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )
                }
            }
        }
    }
}



