/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.reader

import android.app.SearchManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.reader.Checkpoint
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.domain.repository.BookmarkRepository
import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.domain.repository.NoteRepository
import raf.console.chitalka.domain.translation.BookTranslationStatus
import raf.console.chitalka.domain.ui.UIText
import raf.console.chitalka.domain.use_case.book.GetBookById
import raf.console.chitalka.domain.use_case.book.GetText
import raf.console.chitalka.domain.use_case.book.UpdateBook
import raf.console.chitalka.domain.use_case.history.GetLatestHistory
import raf.console.chitalka.domain.use_case.translation.GetBookTranslationLanguages
import raf.console.chitalka.domain.use_case.translation.TranslateBook
import raf.console.chitalka.presentation.core.util.coerceAndPreventNaN
import raf.console.chitalka.presentation.core.util.launchActivity
import raf.console.chitalka.presentation.core.util.setBrightness
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.ui.history.HistoryScreen
import raf.console.chitalka.ui.library.LibraryScreen
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

private const val READER = "READER, MODEL"
private const val BOOK_TRANSLATION_UI = "BOOK_TRANSLATION_UI"

@HiltViewModel
class ReaderModel @Inject constructor(
    private val getBookById: GetBookById,
    private val updateBook: UpdateBook,
    private val getText: GetText,
    private val getLatestHistory: GetLatestHistory,
    private val getBookTranslationLanguages: GetBookTranslationLanguages,
    private val translateBook: TranslateBook,
    private val dataStoreRepository: DataStoreRepository,
    private val bookmarkRepository: BookmarkRepository, // ← добавили
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val mutex = Mutex()

    private val _state = MutableStateFlow(ReaderState())
    val state = _state.asStateFlow()

    private var eventJob = SupervisorJob()
    private var resetJob: Job? = null

    private var scrollJob: Job? = null

    private var notesJob: Job? = null
    private var translationJob: Job? = null
    private var translationTimerJob: Job? = null

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> = _bookmarks


    /*private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes*/



    fun onEvent(event: ReaderEvent) {
        viewModelScope.launch(eventJob + Dispatchers.Main) {
            when (event) {
                is ReaderEvent.OnLoadText -> {
                    launch(Dispatchers.IO) {
                        val text = getText.execute(_state.value.book.id)
                        yield()

                        if (text.isEmpty()) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = UIText.StringResource(R.string.error_could_not_get_text)
                                )
                            }
                            systemBarsVisibility(show = true, activity = event.activity)
                            return@launch
                        }

                        systemBarsVisibility(
                            show = !event.fullscreenMode,
                            activity = event.activity
                        )

                        val lastOpened = getLatestHistory.execute(_state.value.book.id)?.time
                        yield()

                        _state.update {
                            it.copy(
                                showMenu = false,
                                book = it.book.copy(
                                    lastOpened = lastOpened
                                ),
                                text = text,
                                originalText = text,
                                translatedText = null,
                                showTranslatedText = false
                            )
                        }

                        yield()

                        updateBook.execute(_state.value.book)

                        LibraryScreen.refreshListChannel.trySend(0)
                        HistoryScreen.refreshListChannel.trySend(0)

                        launch {
                            snapshotFlow {
                                _state.value.listState.layoutInfo.totalItemsCount
                            }.collectLatest { itemsCount ->
                                if (itemsCount < _state.value.text.size) return@collectLatest

                                _state.value.book.apply {
                                    _state.value.listState.requestScrollToItem(
                                        scrollIndex,
                                        scrollOffset
                                    )
                                    updateChapter(index = scrollIndex)
                                }

                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = null
                                    )
                                }

                                return@collectLatest
                            }
                        }

                    }
                    //observeNotes(state.value.book.id.toLong())
                }

                is ReaderEvent.OnMenuVisibility -> {
                    launch {
                        if (_state.value.lockMenu) return@launch

                        yield()

                        systemBarsVisibility(
                            show = event.show || !event.fullscreenMode,
                            activity = event.activity
                        )
                        _state.update {
                            it.copy(
                                showMenu = event.show,
                                checkpoint = _state.value.listState.run {
                                    if (!event.show || !event.saveCheckpoint) return@run it.checkpoint

                                    Checkpoint(firstVisibleItemIndex, firstVisibleItemScrollOffset)
                                }
                            )
                        }
                    }
                }

                is ReaderEvent.OnChangeProgress -> {
                    launch(Dispatchers.IO) {
                        _state.update {
                            it.copy(
                                book = it.book.copy(
                                    progress = event.progress,
                                    scrollIndex = event.firstVisibleItemIndex,
                                    scrollOffset = event.firstVisibleItemOffset
                                )
                            )
                        }

                        updateBook.execute(_state.value.book)

                        LibraryScreen.refreshListChannel.trySend(300)
                        HistoryScreen.refreshListChannel.trySend(300)
                    }
                }

                is ReaderEvent.OnScrollToChapter -> {
                    launch {
                        _state.value.apply {
                            val chapterIndex = text.indexOf(event.chapter).takeIf { it != -1 }
                            if (chapterIndex == null) {
                                return@launch
                            }

                            listState.requestScrollToItem(chapterIndex)
                            updateChapter(index = chapterIndex)
                            onEvent(
                                ReaderEvent.OnChangeProgress(
                                    progress = calculateProgress(chapterIndex),
                                    firstVisibleItemIndex = chapterIndex,
                                    firstVisibleItemOffset = 0
                                )
                            )
                        }
                    }
                }

                is ReaderEvent.OnScroll -> {
                    scrollJob?.cancel()
                    scrollJob = launch {
                        delay(300)
                        yield()

                        val scrollTo = (_state.value.text.lastIndex * event.progress).roundToInt()
                        _state.value.listState.requestScrollToItem(scrollTo)
                        updateChapter(scrollTo)
                    }
                }

                is ReaderEvent.OnRestoreCheckpoint -> {
                    launch {
                        _state.value.apply {
                            listState.requestScrollToItem(
                                checkpoint.index,
                                checkpoint.offset
                            )

                            updateChapter(checkpoint.index)
                            onEvent(
                                ReaderEvent.OnChangeProgress(
                                    progress = calculateProgress(checkpoint.index),
                                    firstVisibleItemIndex = checkpoint.index,
                                    firstVisibleItemOffset = checkpoint.offset,
                                )
                            )
                        }
                    }
                }

                is ReaderEvent.OnLeave -> {
                    launch {
                        yield()

                        _state.update {
                            it.copy(
                                lockMenu = true
                            )
                        }

                        _state.value.listState.apply {
                            if (
                                _state.value.isLoading ||
                                layoutInfo.totalItemsCount < 1 ||
                                _state.value.text.isEmpty() ||
                                _state.value.errorMessage != null
                            ) return@apply

                            _state.update {
                                it.copy(
                                    book = it.book.copy(
                                        progress = calculateProgress(),
                                        scrollIndex = firstVisibleItemIndex,
                                        scrollOffset = firstVisibleItemScrollOffset
                                    )
                                )
                            }

                            updateBook.execute(_state.value.book)

                            LibraryScreen.refreshListChannel.trySend(0)
                            HistoryScreen.refreshListChannel.trySend(0)
                        }

                        WindowCompat.getInsetsController(
                            event.activity.window,
                            event.activity.window.decorView
                        ).show(WindowInsetsCompat.Type.systemBars())
                        event.activity.setBrightness(brightness = null)

                        event.navigate()
                    }
                }

                is ReaderEvent.OnOpenTranslator -> {
                    launch {
                        val translatorIntent = Intent()
                        val browserIntent = Intent()

                        translatorIntent.type = "text/plain"
                        translatorIntent.action = Intent.ACTION_PROCESS_TEXT
                        browserIntent.action = Intent.ACTION_WEB_SEARCH

                        translatorIntent.putExtra(
                            Intent.EXTRA_PROCESS_TEXT,
                            event.textToTranslate
                        )
                        translatorIntent.putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, true)
                        browserIntent.putExtra(
                            SearchManager.QUERY,
                            "translate: ${event.textToTranslate.trim()}"
                        )

                        yield()

                        translatorIntent.launchActivity(
                            activity = event.activity,
                            createChooser = !event.translateWholeParagraph,
                            success = {
                                return@launch
                            }
                        )
                        browserIntent.launchActivity(
                            activity = event.activity,
                            success = {
                                return@launch
                            }
                        )

                        withContext(Dispatchers.Main) {
                            event.activity.getString(R.string.error_no_translator)
                                .showToast(context = event.activity, longToast = false)
                        }
                    }
                }

                is ReaderEvent.OnOpenShareApp -> {
                    launch {
                        val shareIntent = Intent()

                        shareIntent.action = Intent.ACTION_SEND
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(
                            Intent.EXTRA_SUBJECT,
                            event.activity.getString(R.string.app_name)
                        )
                        shareIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            event.textToShare.trim()
                        )

                        yield()

                        shareIntent.launchActivity(
                            activity = event.activity,
                            createChooser = true,
                            success = {
                                return@launch
                            }
                        )

                        withContext(Dispatchers.Main) {
                            event.activity.getString(R.string.error_no_share_app)
                                .showToast(context = event.activity, longToast = false)
                        }
                    }
                }

                is ReaderEvent.OnOpenWebBrowser -> {
                    launch {
                        val browserIntent = Intent()

                        browserIntent.action = Intent.ACTION_WEB_SEARCH
                        browserIntent.putExtra(
                            SearchManager.QUERY,
                            event.textToSearch
                        )

                        yield()

                        browserIntent.launchActivity(
                            activity = event.activity,
                            success = {
                                return@launch
                            }
                        )

                        withContext(Dispatchers.Main) {
                            event.activity.getString(R.string.error_no_browser)
                                .showToast(context = event.activity, longToast = false)
                        }
                    }
                }

                is ReaderEvent.OnOpenDictionary -> {
                    launch {
                        val dictionaryIntent = Intent()
                        val browserIntent = Intent()

                        dictionaryIntent.type = "text/plain"
                        dictionaryIntent.action = Intent.ACTION_PROCESS_TEXT
                        dictionaryIntent.putExtra(
                            Intent.EXTRA_PROCESS_TEXT,
                            event.textToDefine.trim()
                        )
                        dictionaryIntent.putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, true)

                        browserIntent.action = Intent.ACTION_VIEW
                        val text = event.textToDefine.trim().replace(" ", "+")
                        browserIntent.data = "https://www.onelook.com/?w=$text".toUri()

                        yield()

                        dictionaryIntent.launchActivity(
                            activity = event.activity,
                            createChooser = true,
                            success = {
                                return@launch
                            }
                        )

                        browserIntent.launchActivity(
                            activity = event.activity,
                            success = {
                                return@launch
                            }
                        )

                        withContext(Dispatchers.Main) {
                            event.activity.getString(R.string.error_no_dictionary)
                                .showToast(context = event.activity, longToast = false)
                        }
                    }
                }

                is ReaderEvent.OnShowSettingsBottomSheet -> {
                    _state.update {
                        it.copy(
                            bottomSheet = ReaderScreen.SETTINGS_BOTTOM_SHEET,
                            drawer = null
                        )
                    }
                }

                is ReaderEvent.OnDismissBottomSheet -> {
                    _state.update {
                        it.copy(
                            bottomSheet = null
                        )
                    }
                }

                is ReaderEvent.OnShowChaptersDrawer -> {
                    _state.update {
                        it.copy(
                            drawer = ReaderScreen.CHAPTERS_DRAWER,
                            bottomSheet = null
                        )
                    }

                    noteRepository.getNotesForBook(state.value.book.id.toLong()).collect { notes ->
                        println("🔥 NOTES OBSERVED: $notes")
                        _state.update { it.copy(notes = notes) }
                    }

                    //startObservingNotes(_state.value.book.id.toLong())
                    //observeNotesIfNeeded(state.value.book.id.toLong())
                }

                is ReaderEvent.OnDismissDrawer -> {
                    _state.update {
                        it.copy(
                            drawer = null
                        )
                    }
                }

                is ReaderEvent.OnShowBookTranslationDialog -> {
                    Log.i(BOOK_TRANSLATION_UI, "Show book translation dialog")
                    _state.update {
                        it.copy(
                            showBookTranslationDialog = true,
                            bookTranslationError = null
                        )
                    }
                }

                is ReaderEvent.OnDismissBookTranslationDialog -> {
                    _state.update {
                        it.copy(
                            showBookTranslationDialog = false,
                            bookTranslationError = null
                        )
                    }
                }

                is ReaderEvent.OnSelectSourceBookTranslationLanguage -> {
                    Log.i(
                        BOOK_TRANSLATION_UI,
                        "Select source language ${event.language.languageTag}/${event.language.mlKitTag}"
                    )
                    _state.update {
                        it.copy(selectedSourceTranslationLanguage = event.language)
                    }
                }

                is ReaderEvent.OnSelectTargetBookTranslationLanguage -> {
                    Log.i(
                        BOOK_TRANSLATION_UI,
                        "Select target language ${event.language.languageTag}/${event.language.mlKitTag}"
                    )
                    _state.update {
                        it.copy(selectedTargetTranslationLanguage = event.language)
                    }
                }

                is ReaderEvent.OnStartBookTranslation -> {
                    startBookTranslation(
                        keepPartialOnCancel = event.keepPartialOnCancel
                    )
                }

                is ReaderEvent.OnCancelBookTranslation -> {
                    Log.i(BOOK_TRANSLATION_UI, "Cancel book translation")
                    translationJob?.cancel()
                    translationTimerJob?.cancel()
                    translationJob = null
                    translationTimerJob = null
                    _state.update {
                        val translated = it.translatedText
                        it.copy(
                            isBookTranslationRunning = false,
                            bookTranslationProgress = 0f,
                            bookTranslationStatus = BookTranslationStatus.Idle,
                            bookTranslationMessage = null,
                            bookTranslationElapsedSeconds = 0L,
                            showTranslatedText = it.keepPartialBookTranslationOnCancel && translated != null,
                            text = if (it.keepPartialBookTranslationOnCancel && translated != null) {
                                translated
                            } else {
                                it.originalText
                            }
                        )
                    }
                }

                is ReaderEvent.OnRestoreOriginalText -> {
                    Log.i(BOOK_TRANSLATION_UI, "Restore original text")
                    translationJob?.cancel()
                    translationTimerJob?.cancel()
                    translationJob = null
                    translationTimerJob = null
                    _state.update {
                        it.copy(
                            showTranslatedText = false,
                            text = it.originalText,
                            isBookTranslationRunning = false,
                            bookTranslationStatus = BookTranslationStatus.Idle,
                            bookTranslationProgress = 0f,
                            bookTranslationMessage = null,
                            bookTranslationElapsedSeconds = 0L,
                            bookTranslationError = null
                        )
                    }
                    updateChapter(_state.value.listState.firstVisibleItemIndex)
                }

                is ReaderEvent.OnShowTranslatedText -> {
                    Log.i(BOOK_TRANSLATION_UI, "Show translated text")
                    _state.update {
                        val translated = it.translatedText ?: return@update it
                        it.copy(
                            showTranslatedText = true,
                            text = translated,
                            bookTranslationError = null
                        )
                    }
                    updateChapter(_state.value.listState.firstVisibleItemIndex)
                }

                is ReaderEvent.OnDismissBookTranslationNotice -> {
                    _state.update {
                        it.copy(bookTranslationNotice = null)
                    }
                }

                is ReaderEvent.OnStartTextToSpeech -> {
                    // Запуск TTS
                    startTTS(context = event.context)
                }

                is ReaderEvent.OnShowNotesBookmarksDrawer -> {
                    _state.update {
                        it.copy(drawer = ReaderScreen.NOTES_BOOKMARKS_DRAWER)
                    }

                    loadBookmarks(event.bookId)
                    //loadNotes(event.bookId)
                    //startObservingNotes(event.bookId)
                    //observeNotes(event.bookId)
                }

                is ReaderEvent.OnAddBookmark -> {
                    val currentBookId: Long = state.value.book.id.toLong() ?: return@launch
                    val currentProgress = state.value.book.progress

                    viewModelScope.launch {
                        bookmarkRepository.insertBookmark(
                            Bookmark(
                                id = 0L,
                                bookId = currentBookId,
                                chapterIndex = event.chapterIndex,
                                offset = event.offset,
                                label = event.text.takeIf { it.isNotBlank() },
                                createdAt = System.currentTimeMillis(),
                                progress = currentProgress // <--- сохраняем текущий прогресс
                            )
                        )
                    }
                }

                is ReaderEvent.OnScrollToBookmark -> {
                    launch {
                        val progress = event.progress ?: 0f
                        onEvent(ReaderEvent.OnScroll(progress))
                    }
                }

                is ReaderEvent.OnDeleteBookmark -> {
                    viewModelScope.launch {
                        bookmarkRepository.deleteBookmark(event.bookmark)
                    }
                }

                is ReaderEvent.OnAddNote -> {
                    val currentBookId: Long = state.value.book.id.toLong() ?: return@launch
                    viewModelScope.launch {
                        println("✅ ADD NOTE: bookId=${event.bookId}, content=${event.content}")
                        noteRepository.insertNote(
                            Note(
                                bookId = currentBookId,//event.bookId,
                                chapterIndex = event.chapterIndex,
                                offsetStart = event.offsetStart,
                                offsetEnd = event.offsetEnd,
                                content = event.content,
                                createdAt = System.currentTimeMillis()
                            )
                        )
                        //observeNotesIfNeeded(currentBookId)
                        noteRepository.getNotesForBook(event.bookId).collect { notes ->
                            println("🔥 NOTES OBSERVED: $notes")
                            _state.update { it.copy(notes = notes) }
                        }
                    }
                }

                is ReaderEvent.OnDeleteNote -> {
                    viewModelScope.launch {
                        noteRepository.deleteNote(event.note)
                        //observeNotesIfNeeded(state.value.book.id.toLong())
                        noteRepository.getNotesForBook(event.note.bookId).collect { notes ->
                            println("🔥 NOTES OBSERVED: $notes")
                            _state.update { it.copy(notes = notes) }
                        }
                    }

                }



                else -> {}
            }
        }
    }

    fun startObservingNotes(bookId: Long) {
        viewModelScope.launch {
            noteRepository.observeNotesForBook(bookId)
                .collect { notes ->
                    println("🔥 NOTES OBSERVED: $notes")
                    _state.update { it.copy(notes = notes) }
                }
        }
    }

    fun startObservingAllNotes() {
        viewModelScope.launch {
            noteRepository.observeAllNotes()
                .collect { notes ->
                    _state.update { it.copy(notes = notes) }
                }
        }
    }



    fun onScrollToBookmark(
        offset: Long,
        chapterIndex: Long,
        text: String,
        listState: LazyListState
    ) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    checkpoint = Checkpoint(
                        index = chapterIndex.toInt(),
                        offset = offset.toInt()
                    ),
                    highlightedText = text
                )
            }
            delay(100)
            listState.scrollToItem(offset.toInt())
        }
    }



    fun init(
        bookId: Int,
        fullscreenMode: Boolean,
        activity: ComponentActivity,
        navigateBack: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val book = getBookById.execute(bookId)

            if (book == null) {
                navigateBack()
                return@launch
            }

            eventJob.cancel()
            resetJob?.cancel()
            eventJob.join()
            resetJob?.join()
            eventJob = SupervisorJob()

            val languages = getBookTranslationLanguages.execute()
            val settings = dataStoreRepository.getAllSettings()
            val defaultSource = languages.firstOrNull {
                it.languageTag == settings.bookTranslationSourceLanguage
            } ?: languages.firstOrNull { it.languageTag == "en" }
                ?: languages.firstOrNull()
            val defaultTarget = languages.firstOrNull {
                it.languageTag == settings.bookTranslationTargetLanguage && it != defaultSource
            } ?: languages.firstOrNull {
                it.languageTag == Locale.getDefault().language && it != defaultSource
            } ?: languages.firstOrNull { it.languageTag == "ru" && it != defaultSource }
            ?: languages.firstOrNull { it != defaultSource }
            ?: defaultSource

            _state.update {
                ReaderState(
                    book = book,
                    translationLanguages = languages,
                    selectedSourceTranslationLanguage = defaultSource,
                    selectedTargetTranslationLanguage = defaultTarget
                )
            }

            // ✅ Загрузим закладки и заметки
            loadBookmarks(book.id.toLong())
            //loadNotes(book.id.toLong())


            onEvent(
                ReaderEvent.OnLoadText(
                    activity = activity,
                    fullscreenMode = fullscreenMode
                )
            )

            observeNotes(book.id.toLong())
        }
    }


    fun observeNotes(bookId: Long) {
        notesJob?.cancel()
        notesJob = viewModelScope.launch {
            noteRepository.getNotesForBook(bookId).collect { notes ->
                println("🔥 NOTES OBSERVED: $notes")
                _state.update { it.copy(notes = notes) }
            }
        }
    }

    fun observeNotesIfNeeded(bookId: Long) {
        if (notesJob != null) return // чтобы не запускать повторно
        notesJob = viewModelScope.launch {
            noteRepository.getNotesForBook(bookId).collect { notes ->
                println("🔥 NOTES OBSERVED: $notes")
                _state.update { it.copy(notes = notes) }
            }
        }
    }

    fun loadBookmarks(bookId: Long) {
        viewModelScope.launch {
            bookmarkRepository.observeBookmarksForBook(bookId).collect { bookmarkList ->
                _state.update { it.copy(bookmarks = bookmarkList) }
            }
        }
    }

    fun loadNotes(bookId: Long) {
        viewModelScope.launch {
            noteRepository.observeNotesForBook(bookId).collect { noteList ->
                _state.update { it.copy(notes = noteList) }
            }
        }
    }

    private fun startBookTranslation(
        keepPartialOnCancel: Boolean
    ) {
        val currentState = _state.value
        val sourceLanguage = currentState.selectedSourceTranslationLanguage
        val targetLanguage = currentState.selectedTargetTranslationLanguage

        if (sourceLanguage == null || targetLanguage == null) {
            Log.w(BOOK_TRANSLATION_UI, "Cannot start translation: source or target language is null")
            viewModelScope.launch {
                _state.update {
                    it.copy(bookTranslationError = UIText.StringValue("Выберите языки перевода"))
                }
            }
            return
        }

        if (sourceLanguage == targetLanguage) {
            Log.w(
                BOOK_TRANSLATION_UI,
                "Cannot start translation: same language ${sourceLanguage.languageTag}"
            )
            viewModelScope.launch {
                _state.update {
                    it.copy(bookTranslationError = UIText.StringValue("Исходный и целевой язык должны отличаться"))
                }
            }
            return
        }

        translationJob?.cancel()
        translationTimerJob?.cancel()
        translationJob = viewModelScope.launch(Dispatchers.Main) {
            val originalText = currentState.originalText.ifEmpty { currentState.text }
            val startIndex = currentState.listState.firstVisibleItemIndex
            Log.i(
                BOOK_TRANSLATION_UI,
                "Start book translation bookId=${currentState.book.id} source=${sourceLanguage.mlKitTag} target=${targetLanguage.mlKitTag} startIndex=$startIndex originalItems=${originalText.size} currentItems=${currentState.text.size}"
            )
            _state.update {
                it.copy(
                    showBookTranslationDialog = false,
                    isBookTranslationRunning = true,
                    bookTranslationProgress = 0f,
                    bookTranslationStatus = BookTranslationStatus.CheckingModels,
                    bookTranslationMessage = "Проверка языковых моделей",
                    bookTranslationElapsedSeconds = 0L,
                    bookTranslationError = null,
                    bookTranslationNotice = null,
                    keepPartialBookTranslationOnCancel = keepPartialOnCancel,
                    translatedText = originalText,
                    showTranslatedText = true,
                    text = originalText
                )
            }

            translationTimerJob?.cancel()
            translationTimerJob = launch {
                while (true) {
                    delay(1000)
                    _state.update {
                        if (!it.isBookTranslationRunning) return@update it
                        it.copy(bookTranslationElapsedSeconds = it.bookTranslationElapsedSeconds + 1)
                    }
                }
            }

            try {
                val translated = translateBook.execute(
                    bookId = currentState.book.id,
                    filePath = currentState.book.filePath,
                    text = originalText,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    startIndex = startIndex,
                    onPartialTranslated = { index, item ->
                        viewModelScope.launch(Dispatchers.Main) {
                            _state.update { state ->
                                val currentTranslated = state.translatedText
                                    ?: state.originalText.ifEmpty { state.text }
                                if (index !in currentTranslated.indices) return@update state

                                val updated = currentTranslated.toMutableList().apply {
                                    this[index] = item
                                }

                                state.copy(
                                    translatedText = updated,
                                    text = if (state.showTranslatedText) updated else state.text
                                )
                            }
                        }
                    },
                    onCacheHit = {
                        viewModelScope.launch(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    bookTranslationNotice = UIText.StringValue("Перевод загружен из кэша")
                                )
                            }
                        }
                    },
                    onProgress = { progress ->
                        Log.i(
                            BOOK_TRANSLATION_UI,
                            "Translation progress status=${progress.status} progress=${progress.progress}"
                        )
                        viewModelScope.launch(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    bookTranslationStatus = progress.status,
                                    bookTranslationProgress = progress.progress,
                                    bookTranslationMessage = progress.message
                                )
                            }
                        }
                    }
                )

                _state.update {
                    it.copy(
                        text = translated,
                        translatedText = translated,
                        showTranslatedText = true,
                        isBookTranslationRunning = false,
                        bookTranslationProgress = 1f,
                        bookTranslationStatus = BookTranslationStatus.Idle,
                        bookTranslationMessage = null,
                        bookTranslationElapsedSeconds = 0L,
                        bookTranslationError = null
                    )
                }
                translationTimerJob?.cancel()
                translationTimerJob = null
                Log.i(BOOK_TRANSLATION_UI, "Book translation applied items=${translated.size}")
                updateChapter(_state.value.listState.firstVisibleItemIndex)
            } catch (_: kotlinx.coroutines.CancellationException) {
                translationTimerJob?.cancel()
                translationTimerJob = null
                Log.w(BOOK_TRANSLATION_UI, "Book translation cancelled")
                _state.update {
                    val translatedText = it.translatedText
                    it.copy(
                        text = if (it.keepPartialBookTranslationOnCancel && translatedText != null) {
                            translatedText
                        } else {
                            it.originalText
                        },
                        showTranslatedText = it.keepPartialBookTranslationOnCancel && translatedText != null,
                        isBookTranslationRunning = false,
                        bookTranslationProgress = 0f,
                        bookTranslationStatus = BookTranslationStatus.Idle,
                        bookTranslationMessage = null,
                        bookTranslationElapsedSeconds = 0L
                    )
                }
            } catch (e: Exception) {
                translationTimerJob?.cancel()
                translationTimerJob = null
                Log.e(BOOK_TRANSLATION_UI, "Book translation failed", e)
                _state.update {
                    it.copy(
                        text = it.originalText,
                        showTranslatedText = false,
                        isBookTranslationRunning = false,
                        bookTranslationProgress = 0f,
                        bookTranslationStatus = BookTranslationStatus.Idle,
                        bookTranslationMessage = null,
                        bookTranslationElapsedSeconds = 0L,
                        bookTranslationError = UIText.StringValue(
                            e.localizedMessage ?: "Не удалось перевести книгу"
                        )
                    )
                }
            }
        }
    }


    fun startTTS(context: Context) {
        val currentText = state.value.text

        val fullText = currentText
            .filterIsInstance<ReaderText.Text>() // только текст
            .joinToString(separator = "\n\n") { it.line.text }

        if (fullText.isBlank()) {
            Log.w("TTS", "Нет текста для озвучивания.")
            return
        }

        lateinit var tts: TextToSpeech

        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.getDefault())

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Язык не поддерживается")
                    return@TextToSpeech
                }

                tts.speak(fullText, TextToSpeech.QUEUE_FLUSH, null, "BOOK_TTS")
            } else {
                Log.e("TTS", "Инициализация TTS не удалась")
            }
        }
    }



    @OptIn(FlowPreview::class)
    fun updateProgress(listState: LazyListState) {
        viewModelScope.launch(Dispatchers.Main) {
            snapshotFlow {
                listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
            }.distinctUntilChanged().debounce(300).collectLatest { (index, offset) ->
                val progress = calculateProgress(index)
                if (progress == _state.value.book.progress) return@collectLatest
                val (currentChapter, currentChapterProgress) = calculateCurrentChapter(index)

                Log.i(
                    READER,
                    "Changed progress|currentChapter: $progress; ${currentChapter?.title}"
                )
                _state.update {
                    it.copy(
                        book = it.book.copy(
                            progress = progress,
                            scrollIndex = index,
                            scrollOffset = offset
                        ),
                        currentChapter = currentChapter,
                        currentChapterProgress = currentChapterProgress
                    )
                }

                updateBook.execute(_state.value.book)

                LibraryScreen.refreshListChannel.trySend(0)
                HistoryScreen.refreshListChannel.trySend(0)
            }
        }
    }

    fun findChapterIndexAndLength(index: Int): Pair<Int, Int> {
        return findCurrentChapter(index)?.let { chapter ->
            _state.value.text.run {
                val startIndex = indexOf(chapter).coerceIn(0, lastIndex)
                val endIndex = (indexOfFirst {
                    it is Chapter && indexOf(it) > startIndex
                }.takeIf { it != -1 }) ?: (lastIndex + 1)

                val currentIndexInChapter = (index - startIndex).coerceAtLeast(1)
                val chapterLength = endIndex - (startIndex + 1)
                currentIndexInChapter to chapterLength
            }
        } ?: (-1 to -1)
    }

    private fun updateChapter(index: Int) {
        viewModelScope.launch {
            val (currentChapter, currentChapterProgress) = calculateCurrentChapter(index)
            _state.update {
                Log.i(
                    READER,
                    "Changed currentChapter|currentChapterProgress:" +
                            " ${currentChapter?.title}($currentChapterProgress)"
                )
                it.copy(
                    currentChapter = currentChapter,
                    currentChapterProgress = currentChapterProgress
                )
            }
        }
    }

    private fun calculateCurrentChapter(index: Int): Pair<Chapter?, Float> {
        val currentChapter = findCurrentChapter(index)
        val currentChapterProgress = currentChapter?.let { chapter ->
            _state.value.text.run {
                val startIndex = indexOf(chapter).coerceIn(0, lastIndex)
                val endIndex = (indexOfFirst {
                    it is Chapter && indexOf(it) > startIndex
                }.takeIf { it != -1 }) ?: (lastIndex + 1)

                val currentIndexInChapter = (index - startIndex).coerceAtLeast(1)
                val chapterLength = endIndex - (startIndex + 1)
                (currentIndexInChapter / chapterLength.toFloat())
            }
        }.coerceAndPreventNaN()

        return currentChapter to currentChapterProgress
    }

    private fun findCurrentChapter(index: Int): Chapter? {
        return try {
            for (textIndex in index downTo 0) {
                val readerText = _state.value.text.getOrNull(textIndex) ?: break
                if (readerText is Chapter) {
                    return readerText
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun findChapterIndexForGlobalIndex(
        text: List<ReaderText>,
        globalIndex: Int
    ): Int {
        var chapterIndex = -1
        for (i in 0..globalIndex.coerceAtMost(text.lastIndex)) {
            val item = text[i]
            if (item is ReaderText.Chapter) {
                chapterIndex++
            }
        }
        return chapterIndex
    }


    private fun calculateProgress(firstVisibleItemIndex: Int? = null): Float {
        return _state.value.run {
            if (
                isLoading ||
                listState.layoutInfo.totalItemsCount == 0 ||
                text.isEmpty() ||
                errorMessage != null
            ) {
                return book.progress
            }

            if ((firstVisibleItemIndex ?: listState.firstVisibleItemIndex) == 0) {
                return 0f
            }

            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.last().index
            if (lastVisibleItemIndex >= text.lastIndex) {
                return 1f
            }

            return@run (firstVisibleItemIndex ?: listState.firstVisibleItemIndex)
                .div(text.lastIndex.toFloat())
                .coerceAndPreventNaN()
        }
    }

    private suspend fun systemBarsVisibility(
        show: Boolean,
        activity: ComponentActivity
    ) {
        withContext(Dispatchers.Main) {
            WindowCompat.getInsetsController(
                activity.window,
                activity.window.decorView
            ).apply {
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                if (show) show(WindowInsetsCompat.Type.systemBars())
                else hide(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    fun resetScreen() {
        resetJob = viewModelScope.launch(Dispatchers.Main) {
            eventJob.cancel()
            translationJob?.cancel()
            translationTimerJob?.cancel()
            translationJob = null
            translationTimerJob = null
            eventJob = SupervisorJob()

            yield()
            _state.update { ReaderState() }
        }
    }

    private suspend inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
        mutex.withLock {
            yield()
            this.value = function(this.value)
        }
    }

    fun findGlobalIndexForBookmark(chapterIndex: Int, offset: Int): Int {
        val text = state.value.text
        var currentChapterIndex = -1

        text.forEachIndexed { index, item ->
            if (item is ReaderText.Chapter) {
                currentChapterIndex++
            }

            if (currentChapterIndex == chapterIndex) {
                println("🔔 FOUND CHAPTER at index=$index for chapterIndex=$chapterIndex")

                var offsetCount = 0
                for (i in index + 1 until text.size) {
                    val element = text[i]
                    if (element is ReaderText.Chapter) {
                        break
                    }

                    if (element is ReaderText.Text) {
                        println("🔹 offset=$offsetCount, i=$i, offset target=$offset")
                        if (offsetCount == offset) {
                            println("✅ Found exact match at i=$i")
                            return i
                        }
                        offsetCount++
                    }
                }

                println("⚠️ Offset not found, fallback return ${(index + 1).coerceAtMost(text.lastIndex)}")
                return (index + 1).coerceAtMost(text.lastIndex)
            }
        }

        println("❗ CHAPTER NOT FOUND, returning 0")
        return 0
    }





    fun onBlinkingHighlight(index: Int) {
        viewModelScope.launch {
            repeat(3) {
                _state.update { it.copy(blinkingHighlightIndex = index) }
                delay(300)
                _state.update { it.copy(blinkingHighlightIndex = null) }
                delay(300)
            }
        }
    }

}
