/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.constants

import raf.console.chitalka.R
import raf.console.chitalka.domain.about.Credit
import raf.console.chitalka.domain.ui.UIText
import raf.console.chitalka.domain.ui.UIText.StringResource

fun provideCredits() = listOf(
    Credit(
        name = "Acclorite/book-story",
        source = "This app is based on a fork of the Acclorite/book-story project. The original project is available under the GPL-3.0 license.",
        credits = listOf(),
        website = "https://github.com/Acclorite/book-story"
    ),
    Credit(
        name = "Tachiyomi (Mihon)",
        source = "GitHub",
        credits = listOf(
            UIText.StringResource(R.string.credits_design),
            UIText.StringResource(R.string.credits_ideas),
            UIText.StringValue("Readme")
        ),
        website = "https://www.github.com/mihonapp/mihon"
    ),
    Credit(
        name = "Kitsune",
        source = "GitHub",
        credits = listOf(
            UIText.StringResource(R.string.credits_updates),
            UIText.StringResource(R.string.credits_ideas)
        ),
        website = "https://www.github.com/Drumber/Kitsune"
    ),
    Credit(
        name = "Voyager",
        source = "Voyager Website",
        credits = listOf(
            UIText.StringResource(R.string.credits_ideas)
        ),
        website = "https://voyager.adriel.cafe/"
    ),
    Credit(
        name = "Material Design Icons",
        source = "Google Fonts",
        credits = listOf(
            UIText.StringResource(R.string.credits_icon)
        ),
        website = "https://fonts.google.com/icons"
    ),
    Credit(
        name = "Material Design Fonts",
        source = "Google Fonts",
        credits = listOf(
            UIText.StringResource(R.string.credits_fonts)
        ),
        website = "https://fonts.google.com"
    ),
    Credit(
        name = "Weblate",
        source = "Hosted Weblate",
        credits = listOf(
            UIText.StringResource(R.string.credits_translation),
            UIText.StringResource(R.string.credits_contribution)
        ),
        website = "https://hosted.weblate.org/projects/book-story"
    ),
    Credit(
        name = "OpenDyslexic Font",
        source = "OpenDyslexic Website",
        credits = listOf(
            UIText.StringResource(R.string.credits_fonts)
        ),
        website = "https://opendyslexic.org"
    ),
    Credit(
        name = "GitLab Badge",
        source = "Censorship Website",
        credits = listOf(
            UIText.StringResource(R.string.credits_icon)
        ),
        website = "https://censorship.no/en/index.html"
    ),
    Credit(
        name = "GitHub Badge",
        source = "GitHub",
        credits = listOf(
            UIText.StringResource(R.string.credits_icon)
        ),
        website = "https://github.com/Kunzisoft/Github-badge"
    ),
    Credit(
        name = "Codeberg Badge",
        source = "Codeberg",
        credits = listOf(
            UIText.StringResource(R.string.credits_icon)
        ),
        website = "https://codeberg.org/Codeberg/GetItOnCodeberg"
    )
)