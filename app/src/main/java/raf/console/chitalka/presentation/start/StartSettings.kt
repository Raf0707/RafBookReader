/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.start

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.navigator.StackEvent
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.start.StartScreen

@Composable
fun StartSettings(
    currentPage: Int,
    stackEvent: StackEvent,
    languages: List<ButtonItem>,
    changeLanguage: (MainEvent.OnChangeLanguage) -> Unit,
    navigateForward: () -> Unit
) {
    StartSettingsScaffold(
        navigateForward = navigateForward
    ) {
        StartContentTransition(
            targetValue = when (currentPage) {
                0 -> StartScreen.GENERAL_SETTINGS
                1 -> StartScreen.APPEARANCE_SETTINGS
                else -> StartScreen.SCAN_SETTINGS
            },
            stackEvent = stackEvent
        ) { page ->
            StartSettingsLayout {
                when (page) {
                    StartScreen.GENERAL_SETTINGS -> {
                        StartSettingsLayoutGeneral(
                            languages = languages,
                            changeLanguage = changeLanguage
                        )
                    }

                    StartScreen.APPEARANCE_SETTINGS -> {
                        StartSettingsLayoutAppearance()
                    }

                    StartScreen.SCAN_SETTINGS -> {
                        StartSettingsLayoutScan()
                    }
                }
            }
        }
    }
}