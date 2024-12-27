package raf.console.chitalka.presentation.screens.settings.nested.browse.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SliderWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel
import raf.console.chitalka.presentation.screens.settings.nested.browse.data.BrowseLayout
import raf.console.chitalka.presentation.ui.ExpandingTransition

/**
 * Browse Grid Size setting.
 * Lets user specify grid size to be displayed.
 */
@Composable
fun BrowseGridSizeSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    ExpandingTransition(visible = state.value.browseLayout == BrowseLayout.GRID) {
        SliderWithTitle(
            value = state.value.browseGridSize
                    to " ${stringResource(R.string.browse_grid_size_per_row)}",
            valuePlaceholder = stringResource(id = R.string.browse_grid_size_auto),
            showPlaceholder = state.value.browseAutoGridSize,
            fromValue = 0,
            toValue = 10,
            title = stringResource(id = R.string.browse_grid_size_option),
            onValueChange = {
                onMainEvent(MainEvent.OnChangeBrowseAutoGridSize(it == 0))
                onMainEvent(
                    MainEvent.OnChangeBrowseGridSize(it)
                )
            }
        )
    }
}