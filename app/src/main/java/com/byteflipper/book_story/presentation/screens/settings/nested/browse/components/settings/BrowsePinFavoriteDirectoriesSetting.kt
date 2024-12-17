package com.byteflipper.book_story.presentation.screens.settings.nested.browse.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel
import com.byteflipper.book_story.presentation.screens.settings.nested.browse.data.BrowseFilesStructure
import com.byteflipper.book_story.presentation.ui.ExpandingTransition

/**
 * Browse Pin Favorite Directories setting.
 * If true, shows all favorite directories in root directory and moves them to the top in nested directories.
 */
@Composable
fun BrowsePinFavoriteDirectoriesSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    ExpandingTransition(visible = state.value.browseFilesStructure == BrowseFilesStructure.DIRECTORIES) {
        SwitchWithTitle(
            selected = state.value.browsePinFavoriteDirectories,
            title = stringResource(id = R.string.browse_pin_favorite_directories_option),
            description = stringResource(id = R.string.browse_pin_favorite_directories_option_desc)
        ) {
            onMainEvent(
                MainEvent.OnChangeBrowsePinFavoriteDirectories(
                    !state.value.browsePinFavoriteDirectories
                )
            )
        }
    }
}