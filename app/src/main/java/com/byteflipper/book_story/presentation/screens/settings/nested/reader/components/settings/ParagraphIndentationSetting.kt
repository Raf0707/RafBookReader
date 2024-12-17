package com.byteflipper.book_story.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SliderWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel
import com.byteflipper.book_story.presentation.screens.settings.nested.reader.data.ReaderTextAlignment
import com.byteflipper.book_story.presentation.ui.ExpandingTransition

/**
 * Paragraph Indentation setting.
 * Changes Reader's paragraph indentation.
 */
@Composable
fun ParagraphIndentationSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    ExpandingTransition(
        visible = state.value.textAlignment != ReaderTextAlignment.CENTER &&
                state.value.textAlignment != ReaderTextAlignment.END
    ) {
        SliderWithTitle(
            value = state.value.paragraphIndentation to "pt",
            fromValue = 0,
            toValue = 12,
            title = stringResource(id = R.string.paragraph_indentation_option),
            onValueChange = {
                onMainEvent(
                    MainEvent.OnChangeParagraphIndentation(it)
                )
            }
        )
    }
}