package raf.console.chitalka.presentation.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.IconButton

/**
 * Navigation Icon Button.
 * Used to display [NavigationBottomSheet].
 */
@Composable
fun NavigationIconButton(
    onClick: Navigator.() -> Unit = { showNavigationBottomSheet() }
) {
    val navigator = LocalNavigatorInstance.current
    IconButton(
        icon = Icons.Default.MoreVert,
        contentDescription = R.string.more_content_desc,
        disableOnClick = false,
    ) {
        navigator.onClick()
    }
}