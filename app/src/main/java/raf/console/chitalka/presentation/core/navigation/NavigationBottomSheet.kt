package raf.console.chitalka.presentation.core.navigation

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.util.Position
import raf.console.chitalka.presentation.core.components.modal_bottom_sheet.ModalBottomSheet
import raf.console.chitalka.util.vk_ad_mob.loadAndShowInterstitialAd

/**
 * Navigation Bottom Sheet.
 * Previously known as More Drop-Down.
 * Bottom sheet allows to have more options and it generally looks better.
 *
 * @param onDismissRequest Dismiss callback.
 */
@Composable
fun NavigationBottomSheet(onDismissRequest: () -> Unit) {
    val onNavigate = LocalNavigator.current
    val context: Context = LocalContext.current

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        sheetGesturesEnabled = true
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                NavigationItem(
                    title = stringResource(id = R.string.about_screen),
                    primary = false,
                    position = Position.TOP
                ) {
                    loadAndShowInterstitialAd(context, 1772675)
                    onNavigate {
                        navigate(screen = Screen.About)
                        hideNavigationBottomSheet()
                    }
                }
            }

            item {
                NavigationItem(
                    title = stringResource(id = R.string.help_screen),
                    primary = false,
                    position = Position.BOTTOM
                ) {
                    loadAndShowInterstitialAd(context, 1772675)
                    onNavigate {
                        navigate(screen = Screen.Help(fromStart = false))
                        hideNavigationBottomSheet()
                    }
                }
            }

            item {
                Spacer(Modifier.height(18.dp))
            }

            item {
                NavigationItem(
                    title = stringResource(id = R.string.settings_screen),
                    primary = true,
                    position = Position.SOLO
                ) {
                    loadAndShowInterstitialAd(context, 1772675)
                    onNavigate {
                        navigate(screen = Screen.Settings)
                        hideNavigationBottomSheet()
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}