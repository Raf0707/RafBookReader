@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.screens.settings.nested.browse.components.subcategories

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import raf.console.chitalka.R
import raf.console.chitalka.presentation.screens.settings.components.SettingsSubcategory
import raf.console.chitalka.presentation.screens.settings.nested.browse.components.settings.BrowseFilesStructureSetting
import raf.console.chitalka.presentation.screens.settings.nested.browse.components.settings.BrowseGridSizeSetting
import raf.console.chitalka.presentation.screens.settings.nested.browse.components.settings.BrowseLayoutSetting
import raf.console.chitalka.presentation.screens.settings.nested.browse.components.settings.BrowsePinFavoriteDirectoriesSetting

/**
 * Browse General subcategory.
 * Contains all settings from General Browse settings.
 */
fun LazyListScope.BrowseGeneralSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.general_browse_settings) },
    showTitle: Boolean = true,
    showDivider: Boolean = true,
    topPadding: Dp,
    bottomPadding: Dp
) {
    SettingsSubcategory(
        titleColor = titleColor,
        title = title,
        showTitle = showTitle,
        showDivider = showDivider,
        topPadding = topPadding,
        bottomPadding = bottomPadding
    ) {
        item {
            BrowseFilesStructureSetting()
        }

        item {
            BrowsePinFavoriteDirectoriesSetting()
        }

        item {
            BrowseLayoutSetting()
        }

        item {
            BrowseGridSizeSetting()
        }
    }
}