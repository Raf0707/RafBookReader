package com.byteflipper.book_story.presentation.screens.settings.nested.general.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.domain.model.ButtonItem
import com.byteflipper.book_story.presentation.core.components.settings.ChipsWithTitle
import com.byteflipper.book_story.presentation.core.constants.Constants
import com.byteflipper.book_story.presentation.core.constants.provideLanguages
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel

/**
 * App Language setting.
 * Changes app's language.
 */
@Composable
fun AppLanguageSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    ChipsWithTitle(
        title = stringResource(id = R.string.language_option),
        chips = Constants.provideLanguages().sortedBy { it.second }.map {
            ButtonItem(
                it.first,
                it.second,
                MaterialTheme.typography.labelLarge,
                it.first == state.value.language
            )
        }.sortedBy { it.title }
    ) {
        onMainEvent(
            MainEvent.OnChangeLanguage(
                it.id
            )
        )
    }
}