/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.help

import android.os.Parcelable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import raf.console.chitalka.presentation.help.HelpContent
import raf.console.chitalka.presentation.navigator.LocalNavigator
import raf.console.chitalka.ui.browse.BrowseScreen
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.start.StartScreen

@Parcelize
data class HelpScreen(val fromStart: Boolean) : Screen, Parcelable {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val mainModel = hiltViewModel<MainModel>()

        val (scrollBehavior, listState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()

        HelpContent(
            fromStart = fromStart,
            scrollBehavior = scrollBehavior,
            listState = listState,
            changeShowStartScreen = mainModel::onEvent,
            navigateToBrowse = {
                navigator.push(BrowseScreen, saveInBackStack = false)
            },
            navigateToStart = {
                mainModel.onEvent(MainEvent.OnChangeShowStartScreen(true))
                navigator.push(StartScreen, saveInBackStack = false)
            },
            navigateBack = {
                navigator.pop()
            }
        )
    }
}