@file:Suppress("FunctionName")

package com.byteflipper.everbook.presentation.screens.settings.nested.browse.components.subcategories

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.screens.settings.components.SettingsSubcategory
import com.byteflipper.everbook.presentation.screens.settings.nested.browse.components.settings.BrowseFilterSetting

/**
 * Browse Filter subcategory.
 * Contains all settings from Filter Browse settings.
 */
fun LazyListScope.BrowseFilterSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.filter_browse_settings) },
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
        BrowseFilterSetting()
    }
}