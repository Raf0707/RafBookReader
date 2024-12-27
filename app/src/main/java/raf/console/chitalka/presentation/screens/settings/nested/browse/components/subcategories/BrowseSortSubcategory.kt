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
import raf.console.chitalka.presentation.screens.settings.nested.browse.components.settings.BrowseSortOrderSetting

/**
 * Browse Sort subcategory.
 * Contains all settings from Sort Browse settings.
 */
fun LazyListScope.BrowseSortSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.sort_browse_settings) },
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
        BrowseSortOrderSetting()
    }
}