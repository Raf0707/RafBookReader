/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.constants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import raf.console.chitalka.R
import raf.console.chitalka.domain.about.Badge

fun provideAboutBadges() = listOf(
    /*Badge(
        id = "donate",
        drawable = R.drawable.github_24,
        imageVector = null,
        contentDescription = R.string.x_content_desc,
        url = "https://www.donationalerts.com/r/raf0707"
    ),*/
    Badge(
        id = "github_profile",
        drawable = R.drawable.github_24,
        imageVector = null,
        contentDescription = R.string.start_source_code,
        url = "https://github.com/Raf0707/Chitalka"
    ),

    Badge(
        id = "other_apps",
        drawable = R.drawable.apps_24px,
        imageVector = null,
        contentDescription = R.string.other_apps,
        url = "https://www.rustore.ru/catalog/developer/90b1826e"
    ),

    Badge(
        id = "rate_app_here",
        drawable = R.drawable.rate_in_app,
        imageVector = null,
        contentDescription = R.string.rate_app,
        url = ""
    ),

    Badge(
        id = "palestine",
        drawable = R.drawable.palestine_ard,
        imageVector = null,
        contentDescription = R.string.freePalestine,
        url = "Free Palestine!"
    ),

    Badge(
        id = "vk",
        drawable = R.drawable.vk_24,
        imageVector = null,
        contentDescription = R.string.vk,
        url = "https://vk.com/mahabbaa"
    ),

    Badge(
        id = "telegram",
        drawable = R.drawable.telegram_24,
        imageVector = null,
        contentDescription = R.string.telegram,
        url = "https://t.me/ibnRustum"
    ),

)