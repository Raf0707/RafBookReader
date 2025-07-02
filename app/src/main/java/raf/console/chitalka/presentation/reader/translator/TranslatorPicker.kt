package raf.console.chitalka.presentation.reader.translator

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

/*@Composable
fun TranslatorPicker() {
    val mainModel = hiltViewModel<MainModel>()
    val state by mainModel.state.collectAsStateWithLifecycle()

    val items = TranslatorApp.values()
    val selected = state.selectedTranslator

    DropdownSelector(
        label = stringResource(R.string.select_translator),
        items = items.map { it.displayName },
        selectedIndex = items.indexOf(selected),
        onItemSelected = { index ->
            mainModel.onEvent(MainEvent.OnSelectTranslator(items[index]))
        }
    )
}*/

fun openExternalTranslator(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

    val chooser = Intent.createChooser(intent, "Translator")
    chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(chooser)
}

fun openSelectedTranslator(context: Context, text: String, translator: TranslatorApp) {
    val url = translator.baseUrl + Uri.encode(text)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}