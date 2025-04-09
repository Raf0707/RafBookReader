/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.about

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.presentation.about.AboutContent
import raf.console.chitalka.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import raf.console.chitalka.presentation.navigator.LocalNavigator
import raf.console.chitalka.ui.credits.CreditsScreen
import raf.console.chitalka.ui.licenses.LicensesScreen

@Parcelize
object AboutScreen : Screen, Parcelable {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = hiltViewModel<AboutModel>()

        val (scrollBehavior, listState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()

        AboutContent(
            scrollBehavior = scrollBehavior,
            listState = listState,
            navigateToBrowserPage = screenModel::onEvent,
            navigateToLicenses = {
                navigator.push(LicensesScreen)
            },
            navigateToCredits = {
                navigator.push(CreditsScreen)
            },
            navigateBack = {
                navigator.pop()
            }
        )
    }
}