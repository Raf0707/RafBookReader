/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.start

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.domain.navigator.StackEvent
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.start.StartScreen

@Composable
fun StartContent(
    currentPage: Int,
    stackEvent: StackEvent,
    languages: List<ButtonItem>,
    changeLanguage: (MainEvent.OnChangeLanguage) -> Unit,
    navigateForward: () -> Unit,
    navigateBack: () -> Unit,
    navigateToBrowse: () -> Unit,
    navigateToHelp: () -> Unit
) {
    StartContentTransition(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        targetValue = when {
            currentPage in 0..2 -> StartScreen.SETTINGS
            currentPage == 3 -> StartScreen.SOURCE_CODE
            currentPage == 4 -> StartScreen.TELEGRAM_GROUP
            //currentPage == 5 -> StartScreen.LICENSE
            currentPage == 5 -> StartScreen.PRIVACY_POLICY
            currentPage == 6 -> StartScreen.USER_AGREEMENT
            currentPage == 7 -> StartScreen.WEB_RAF_CONSOLE
            else -> StartScreen.DONE
        },
        stackEvent = stackEvent
    ) { page ->
        when (page) {
            StartScreen.SETTINGS -> {
                StartSettings(
                    currentPage = currentPage,
                    stackEvent = stackEvent,
                    languages = languages,
                    changeLanguage = changeLanguage,
                    navigateForward = navigateForward
                )
            }
            StartScreen.SOURCE_CODE -> {
                StartSourceCode(
                    navigateForward = navigateForward
                )
            }
            StartScreen.TELEGRAM_GROUP -> {
                StartTelegramGroup(
                    navigateForward = navigateForward
                )
            }
            /*StartScreen.LICENSE -> {
                LicenseAgreementScreen(
                    navigateForward = navigateForward
                )
            }*/
            StartScreen.PRIVACY_POLICY -> {
                PrivacyPolicyScreen(
                    navigateForward = navigateForward
                )
            }
            StartScreen.USER_AGREEMENT -> {
                UserAgreementScreen(
                    navigateForward = navigateForward
                )
            }
            StartScreen.WEB_RAF_CONSOLE -> {
                StartWebRafConsole(
                    navigateForward = navigateForward
                )
            }
            StartScreen.DONE -> {
                StartDone(
                    navigateToBrowse = navigateToBrowse,
                    navigateToHelp = navigateToHelp
                )
            }
        }
    }

    StartBackHandler(
        navigateBack = navigateBack
    )
}