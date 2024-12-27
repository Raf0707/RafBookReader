@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.screens.settings.nested.reader.components.subcategories

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import raf.console.chitalka.R
import raf.console.chitalka.presentation.screens.settings.components.SettingsSubcategory
import raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings.CheckForTextUpdateSetting
import raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings.CheckForTextUpdateToastSetting
import raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings.FullscreenSetting
import raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings.HideBarsOnFastScrollSetting
import raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings.KeepScreenOnSetting

/**
 * Misc subcategory.
 * Contains all settings that cannot be categorized.
 */
fun LazyListScope.MiscSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.misc_reader_settings) },
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
            CheckForTextUpdateSetting()
        }

        item {
            CheckForTextUpdateToastSetting()
        }

        item {
            FullscreenSetting()
        }

        item {
            KeepScreenOnSetting()
        }

        item {
            HideBarsOnFastScrollSetting()
        }
    }
}