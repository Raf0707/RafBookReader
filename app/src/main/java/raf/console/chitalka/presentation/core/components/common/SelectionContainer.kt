/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("DEPRECATION")

package raf.console.chitalka.presentation.core.components.common

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.presentation.reader.translator.TranslatorApp
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel


private const val MENU_ITEM_COPY = 0
private const val MENU_ITEM_SOUND = 1
private const val MENU_ITEM_BOOKMARK = 2
private const val MENU_ITEM_SHARE = 3
private const val MENU_ITEM_WEB = 4
private const val MENU_ITEM_TRANSLATE = 5
private const val MENU_ITEM_DICTIONARY = 6

/**
 * Text ActionMode callback.
 * Used in pair with [SelectionToolbar]. Follow [TextToolbar] for more info.
 */
/*private class TextActionModeCallback(
    private val context: Context,
    var rect: Rect = Rect.Zero,
    var onCopyRequested: (() -> Unit)? = null,
    var onBookmarkRequested: (() -> Unit)? = null,
    var onShareRequested: (() -> Unit)? = null,
    var onWebSearchRequested: (() -> Unit)? = null,
    var onTranslateRequested: (() -> Unit)? = null,
    var onDictionaryRequested: (() -> Unit)? = null
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        requireNotNull(menu)
        requireNotNull(mode)

        onCopyRequested?.let {
            menu.add(0, MENU_ITEM_COPY, 0, context.getString(R.string.copy))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        onBookmarkRequested?.let {
            menu.add(0, MENU_ITEM_BOOKMARK, 1, context.getString(R.string.in_bookmark))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        onShareRequested?.let {
            menu.add(0, MENU_ITEM_SHARE, 2, context.getString(R.string.share))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        onWebSearchRequested?.let {
            menu.add(0, MENU_ITEM_WEB, 3, context.getString(R.string.web_search))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        onTranslateRequested?.let {
            menu.add(0, MENU_ITEM_TRANSLATE, 4, context.getString(R.string.translate))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        onDictionaryRequested?.let {
            menu.add(0, MENU_ITEM_DICTIONARY, 5, context.getString(R.string.dictionary))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            MENU_ITEM_COPY -> onCopyRequested?.invoke()
            MENU_ITEM_SHARE -> onShareRequested?.invoke()
            MENU_ITEM_WEB -> onWebSearchRequested?.invoke()
            MENU_ITEM_TRANSLATE -> onTranslateRequested?.invoke()
            MENU_ITEM_DICTIONARY -> onDictionaryRequested?.invoke()
            else -> return false
        }
        mode?.finish()
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {}
}

/**
 * Floating [TextActionModeCallback].
 */
private class FloatingTextActionModeCallback(
    val callback: TextActionModeCallback
) : ActionMode.Callback2() {
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return callback.onActionItemClicked(mode, item)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return callback.onCreateActionMode(mode, menu)
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return callback.onPrepareActionMode(mode, menu)
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        callback.onDestroyActionMode(mode)
    }

    override fun onGetContentRect(mode: ActionMode?, view: View?, outRect: android.graphics.Rect?) {
        val rect = callback.rect
        outRect?.set(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )
    }
}*/

private class TextActionModeCallback(
    private val context: Context,
    var rect: Rect = Rect.Zero,
    var onCopyRequested: (() -> Unit)? = null,
    var onSoundRequested: (() -> Unit)? = null,
    var onBookmarkRequested: (() -> Unit)? = null,
    var onShareRequested: (() -> Unit)? = null,
    var onWebSearchRequested: (() -> Unit)? = null,
    var onTranslateRequested: (() -> Unit)? = null,
    var onDictionaryRequested: (() -> Unit)? = null
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        requireNotNull(menu)
        requireNotNull(mode)

        onCopyRequested?.let {
            menu.add(0, MENU_ITEM_COPY, 0, context.getString(R.string.copy))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        onSoundRequested?.let {
            menu.add(0, MENU_ITEM_SOUND, 1, context.getString(R.string.sound))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        onBookmarkRequested?.let {
            menu.add(0, MENU_ITEM_BOOKMARK, 2, context.getString(R.string.in_bookmark))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        onShareRequested?.let {
            menu.add(0, MENU_ITEM_SHARE, 3, context.getString(R.string.share))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        onWebSearchRequested?.let {
            menu.add(0, MENU_ITEM_WEB, 4, context.getString(R.string.web_search))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        onTranslateRequested?.let {
            menu.add(0, MENU_ITEM_TRANSLATE, 5, context.getString(R.string.translate))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        onDictionaryRequested?.let {
            menu.add(0, MENU_ITEM_DICTIONARY, 6, context.getString(R.string.dictionary))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            MENU_ITEM_COPY -> onCopyRequested?.invoke()
            MENU_ITEM_SOUND -> onSoundRequested?.invoke()
            MENU_ITEM_BOOKMARK -> onBookmarkRequested?.invoke()
            MENU_ITEM_SHARE -> onShareRequested?.invoke()
            MENU_ITEM_WEB -> onWebSearchRequested?.invoke()
            MENU_ITEM_TRANSLATE -> onTranslateRequested?.invoke()
            MENU_ITEM_DICTIONARY -> onDictionaryRequested?.invoke()
            else -> return false
        }
        mode?.finish()
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {}
}

private class FloatingTextActionModeCallback(val callback: TextActionModeCallback) : ActionMode.Callback2() {
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = callback.onActionItemClicked(mode, item)
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = callback.onCreateActionMode(mode, menu)
    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = callback.onPrepareActionMode(mode, menu)
    override fun onDestroyActionMode(mode: ActionMode?) = callback.onDestroyActionMode(mode)
    override fun onGetContentRect(mode: ActionMode?, view: View?, outRect: android.graphics.Rect?) {
        val rect = callback.rect
        outRect?.set(rect.left.toInt(), rect.top.toInt(), rect.right.toInt(), rect.bottom.toInt())
    }
}

/**
 * Selection Toolbar.
 * Used in pair with [SelectionContainer] to display custom toolbar.
 */
/*private class SelectionToolbar(
    private val view: View,
    context: Context,
    private val onCopyRequest: (() -> Unit)?,
    private val onBookmarkRequested: (() -> Unit)?,
    private val onShareRequest: ((String) -> Unit)?,
    private val onWebSearchRequest: ((String) -> Unit)?,
    private val onTranslateRequest: ((String) -> Unit)?,
    private val onDictionaryRequest: ((String) -> Unit)?
) : TextToolbar {
    private var actionMode: ActionMode? = null
    private val callback = TextActionModeCallback(context = context)

    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    override var status: TextToolbarStatus by mutableStateOf(TextToolbarStatus.Hidden)

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        callback.rect = rect
        callback.onCopyRequested = {
            onCopyRequested?.invoke()
            onCopyRequest?.invoke()
        }
        callback.onShareRequested = {
            val previousClipboard = clipboardManager.primaryClip
            onCopyRequested?.invoke()
            val currentClipboard = clipboardManager.text

            onShareRequest?.invoke(currentClipboard.toString())

            if (previousClipboard != null) {
                clipboardManager.setPrimaryClip(
                    previousClipboard
                )
            } else {
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, " "))
            }
        }
        callback.onWebSearchRequested = {
            val previousClipboard = clipboardManager.primaryClip
            onCopyRequested?.invoke()
            val currentClipboard = clipboardManager.text

            onWebSearchRequest?.invoke(currentClipboard.toString())

            if (previousClipboard != null) {
                clipboardManager.setPrimaryClip(
                    previousClipboard
                )
            } else {
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, " "))
            }
        }
        callback.onTranslateRequested = {
            val previousClipboard = clipboardManager.primaryClip
            onCopyRequested?.invoke()
            val currentClipboard = clipboardManager.text

            onTranslateRequest?.invoke(currentClipboard.toString())

            if (previousClipboard != null) {
                clipboardManager.setPrimaryClip(
                    previousClipboard
                )
            } else {
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, " "))
            }
        }
        callback.onDictionaryRequested = {
            val previousClipboard = clipboardManager.primaryClip
            onCopyRequested?.invoke()
            val currentClipboard = clipboardManager.text

            onDictionaryRequest?.invoke(currentClipboard.toString())

            if (previousClipboard != null) {
                clipboardManager.setPrimaryClip(
                    previousClipboard
                )
            } else {
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, " "))
            }
        }

        if (actionMode == null) {
            status = TextToolbarStatus.Shown
            actionMode = view.startActionMode(
                FloatingTextActionModeCallback(callback),
                ActionMode.TYPE_FLOATING
            )
        } else {
            actionMode?.invalidate()
        }
    }

    override fun hide() {
        status = TextToolbarStatus.Hidden
        actionMode?.finish()
        actionMode = null
    }
}*/
private class SelectionToolbar(
    private val view: View,
    context: Context,
    private val selectedTranslator: TranslatorApp, // 👈 передаём выбранный переводчик
    private val onCopyRequest: (() -> Unit)? = null,
    private val onSoundRequested: ((String) -> Unit)? = null,
    private val onBookmarkRequested: ((String) -> Unit)? = null,
    private val onShareRequest: ((String) -> Unit)? = null,
    private val onWebSearchRequest: ((String) -> Unit)? = null,
    private val onTranslateRequest: ((String) -> Unit)? = null,
    private val onDictionaryRequest: ((String) -> Unit)? = null
) : TextToolbar {
    private var actionMode: ActionMode? = null
    private val callback = TextActionModeCallback(context)
    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    override var status: TextToolbarStatus by mutableStateOf(TextToolbarStatus.Hidden)

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        callback.rect = rect

        fun getText(): String {
            val previousClipboard = clipboardManager.primaryClip
            onCopyRequested?.invoke()
            val selected = clipboardManager.text.toString()
            previousClipboard?.let { clipboardManager.setPrimaryClip(it) }
            return selected
        }

        val context = view.context

        callback.onCopyRequested = {
            onCopyRequested?.invoke()
            onCopyRequest?.invoke()
        }

        callback.onSoundRequested = {
            onSoundRequested?.invoke(getText())
        }

        callback.onBookmarkRequested = {
            onBookmarkRequested?.invoke(getText())
        }

        callback.onShareRequested = {
            onShareRequest?.invoke(getText())
        }

        callback.onWebSearchRequested = {
            onWebSearchRequest?.invoke(getText())
        }

        callback.onTranslateRequested = {
            onTranslateRequest?.invoke(getText())

            val selectedText = getText()
            val encodedText = Uri.encode(selectedText)

            val urls = listOf(
                "https://translate.google.com/?sl=auto&tl=auto&text=$encodedText",
                "https://translate.yandex.com/?text=$encodedText",
                "https://www.deepl.com/translator#auto/en/$encodedText"
            )

            val initialIntents = urls.map { url ->
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
            }.toTypedArray()

            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urls.first()))
            val chooser = Intent.createChooser(fallbackIntent, context.getString(R.string.translate))
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, initialIntents)

            try {
                context.startActivity(chooser)
            } catch (e: Exception) {
                Log.e("TranslatorChooser", "No app found to handle translator URL", e)
            }

        }

        callback.onDictionaryRequested = {
            onDictionaryRequest?.invoke(getText())
        }

        if (actionMode == null) {
            status = TextToolbarStatus.Shown
            actionMode = view.startActionMode(FloatingTextActionModeCallback(callback), ActionMode.TYPE_FLOATING)
        } else {
            actionMode?.invalidate()
        }
    }

    override fun hide() {
        status = TextToolbarStatus.Hidden
        actionMode?.finish()
        actionMode = null
    }
}


/**
 * Selection container.
 *
 * @param onCopyRequested Callback for when the copy option is clicked.
 * @param onTranslateRequested Callback for when the translate option is clicked.
 * @param onDictionaryRequested Callback for when the dictionary option is clicked.
 * @param content Selection container content.
 */

@Composable
fun SelectionContainer(
    onCopyRequested: (() -> Unit),
    onSoundRequested: ((String) -> Unit),
    onBookmarkRequested: ((String) -> Unit),
    onShareRequested: ((String) -> Unit),
    onWebSearchRequested: ((String) -> Unit),
    onTranslateRequested: ((String) -> Unit),
    onDictionaryRequested: ((String) -> Unit),
    selectedTranslator: TranslatorApp, // 👈 обязательно передаём выбранный переводчик
    content: @Composable (toolbarHidden: Boolean) -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current

    val selectionToolbar = remember(selectedTranslator) {
        SelectionToolbar(
            view = view,
            context = context,
            selectedTranslator = selectedTranslator,
            onCopyRequest = {
                onCopyRequested()
            },
            onSoundRequested = {
                onSoundRequested(it)
            },
            onBookmarkRequested = {
                onBookmarkRequested(it)
            },
            onShareRequest = {
                onShareRequested(it)
            },
            onWebSearchRequest = {
                onWebSearchRequested(it)
            },
            onTranslateRequest = {
                onTranslateRequested(it)
            },
            onDictionaryRequest = {
                onDictionaryRequested(it)
            }
        )
    }

    val isToolbarHidden = remember(selectionToolbar.status) {
        derivedStateOf {
            selectionToolbar.status == TextToolbarStatus.Hidden
        }
    }

    CompositionLocalProvider(LocalTextToolbar provides selectionToolbar) {
        androidx.compose.foundation.text.selection.SelectionContainer {
            content(isToolbarHidden.value)
        }
    }
}




/*@Composable
fun SelectionContainer(
    onCopyRequested: (() -> Unit),
    onShareRequested: ((String) -> Unit),
    onWebSearchRequested: ((String) -> Unit),
    onTranslateRequested: ((String) -> Unit),
    onDictionaryRequested: ((String) -> Unit),
    content: @Composable (toolbarHidden: Boolean) -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current

    val selectionToolbar = remember {
        SelectionToolbar(
            view = view,
            context = context,
            onCopyRequest = onCopyRequested,
            onShareRequest = onShareRequested,
            onWebSearchRequest = onWebSearchRequested,
            onTranslateRequest = onTranslateRequested,
            onDictionaryRequest = onDictionaryRequested
        )
    }

    val isToolbarHidden = remember(selectionToolbar.status) {
        derivedStateOf {
            selectionToolbar.status == TextToolbarStatus.Hidden
        }
    }

    CompositionLocalProvider(
        LocalTextToolbar provides selectionToolbar
    ) {
        androidx.compose.foundation.text.selection.SelectionContainer {
            content(isToolbarHidden.value)
        }
    }

}*/

/*@Composable
fun SelectionContainer1(
    onCopyRequested: (() -> Unit),
    onShareRequested: ((String) -> Unit),
    onWebSearchRequested: ((String) -> Unit),
    onTranslateRequested: ((String) -> Unit),
    onDictionaryRequested: ((String) -> Unit),
    content: @Composable (toolbarHidden: Boolean) -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current

    val selectionToolbar = remember {
        SelectionToolbar(
            view = view,
            context = context,
            onCopyRequest = onCopyRequested,
            onShareRequest = onShareRequested,
            onWebSearchRequest = onWebSearchRequested,
            onTranslateRequest = onTranslateRequested,
            onDictionaryRequest = onDictionaryRequested
        )
    }

    val isToolbarHidden by remember {
        derivedStateOf {
            selectionToolbar.status == TextToolbarStatus.Hidden
        }
    }

    CompositionLocalProvider(LocalTextToolbar provides selectionToolbar) {
        // ⚠️ ВАЖНО: не вкладывай SelectionContainer в другие SelectionContainer'ы
        // иначе возникнет крах при множественном выделении.
        androidx.compose.foundation.text.selection.SelectionContainer {
            content(isToolbarHidden)
        }
    }
}

@Composable
fun SafeSelectionContainer(
    onCopyRequested: (() -> Unit),
    onShareRequested: ((String) -> Unit),
    onWebSearchRequested: ((String) -> Unit),
    onTranslateRequested: ((String) -> Unit),
    onDictionaryRequested: ((String) -> Unit),
    content: @Composable (toolbarHidden: Boolean) -> Unit
) {
    val view = LocalView.current
    val context = LocalContext.current

    val selectionToolbar = remember {
        SelectionToolbar(
            view = view,
            context = context,
            onCopyRequest = onCopyRequested,
            onShareRequest = onShareRequested,
            onWebSearchRequest = onWebSearchRequested,
            onTranslateRequest = onTranslateRequested,
            onDictionaryRequest = onDictionaryRequested
        )
    }

    val isToolbarHidden = remember(selectionToolbar.status) {
        derivedStateOf {
            selectionToolbar.status == TextToolbarStatus.Hidden
        }
    }

    CompositionLocalProvider(LocalTextToolbar provides selectionToolbar) {
        // Мы оборачиваем в обычный SelectionContainer только 1 блок текста,
        // поэтому ошибок не будет при множественном выделении
        androidx.compose.foundation.text.selection.SelectionContainer {
            content(isToolbarHidden.value)
        }
    }
}*/



