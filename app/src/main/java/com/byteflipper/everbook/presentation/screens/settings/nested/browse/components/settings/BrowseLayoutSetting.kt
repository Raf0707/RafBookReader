package com.byteflipper.everbook.presentation.screens.settings.nested.browse.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.domain.model.ButtonItem
import com.byteflipper.everbook.presentation.core.components.settings.SegmentedButtonWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel
import com.byteflipper.everbook.presentation.screens.settings.nested.browse.data.BrowseLayout

/**
 * Browse Layout setting.
 * Lets user choose between layouts(list, grid).
 */
@Composable
fun BrowseLayoutSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.browse_layout_option),
        buttons = BrowseLayout.entries.map {
            ButtonItem(
                it.toString(),
                when (it) {
                    BrowseLayout.LIST -> stringResource(id = R.string.browse_layout_list)
                    BrowseLayout.GRID -> stringResource(id = R.string.browse_layout_grid)
                },
                MaterialTheme.typography.labelLarge,
                it == state.value.browseLayout
            )
        }
    ) {
        onMainEvent(
            MainEvent.OnChangeBrowseLayout(
                it.id
            )
        )
    }
}