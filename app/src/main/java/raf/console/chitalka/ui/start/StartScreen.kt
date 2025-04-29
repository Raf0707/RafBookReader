/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.start

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.bouncycastle.LICENSE
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.domain.navigator.StackEvent
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.constants.provideLanguages
import raf.console.chitalka.presentation.core.util.LocalActivity
import raf.console.chitalka.presentation.navigator.LocalNavigator
import raf.console.chitalka.presentation.start.StartContent
import raf.console.chitalka.ui.browse.BrowseScreen
import raf.console.chitalka.ui.help.HelpScreen
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Parcelize
object StartScreen : Screen, Parcelable {

    @IgnoredOnParcel
    const val SETTINGS = "settings"

    @IgnoredOnParcel
    const val GENERAL_SETTINGS = "general_settings"

    @IgnoredOnParcel
    const val APPEARANCE_SETTINGS = "appearance_settings"

    @IgnoredOnParcel
    const val SCAN_SETTINGS = "scan_settings"

    @IgnoredOnParcel
    const val SOURCE_CODE = "source_code"

    @IgnoredOnParcel
    const val TELEGRAM_GROUP = "telegram_group"

    @IgnoredOnParcel
    const val LICENSE = "license"

    @IgnoredOnParcel
    const val PRIVACY_POLICY = "privacy_policy"

    @IgnoredOnParcel
    const val USER_AGREEMENT  = "user_agreement"

    @IgnoredOnParcel
    const val WEB_RAF_CONSOLE  = "web_raf_console"

    @IgnoredOnParcel
    const val DONE = "done"

    @SuppressLint("InlinedApi")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val mainModel = hiltViewModel<MainModel>()

        val mainState = mainModel.state.collectAsStateWithLifecycle()

        val activity = LocalActivity.current

        val currentPage = remember { mutableIntStateOf(0) }
        val stackEvent = remember { mutableStateOf(StackEvent.Default) }

        val languages = remember(mainState.value.language) {
            provideLanguages().sortedBy { it.second }.map {
                ButtonItem(
                    id = it.first,
                    title = it.second,
                    textStyle = TextStyle(),
                    selected = it.first == mainState.value.language
                )
            }.sortedBy { it.title }
        }

        StartContent(
            currentPage = currentPage.intValue,
            stackEvent = stackEvent.value,
            languages = languages,
            changeLanguage = mainModel::onEvent,
            navigateForward = {
                if (currentPage.intValue + 1 == 10) {
                    return@StartContent
                }

                stackEvent.value = StackEvent.Default
                currentPage.intValue += 1
            },
            navigateBack = {
                if ((currentPage.intValue - 1) < 0) {
                    activity.finish()
                } else {
                    stackEvent.value = StackEvent.Pop
                    currentPage.intValue -= 1
                }
            },
            navigateToBrowse = {
                navigator.push(
                    BrowseScreen,
                    saveInBackStack = false
                )
                BrowseScreen.refreshListChannel.trySend(Unit)
                mainModel.onEvent(MainEvent.OnChangeShowStartScreen(false))
            },
            navigateToHelp = {
                navigator.push(
                    HelpScreen(fromStart = true),
                    saveInBackStack = false
                )
            },
        )
    }
}