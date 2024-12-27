package raf.console.chitalka.presentation.core.constants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import raf.console.chitalka.R
import raf.console.chitalka.domain.model.Badge

fun Constants.provideAboutBadges() = listOf(
    Badge(
        id = "donate",
        drawable = R.drawable.baseline_donate_on_24,
        imageVector = null,
        contentDescription = R.string.x_content_desc,
        url = "https://www.donationalerts.com/r/raf0707"
    ),
    Badge(
        id = "vk",
        drawable = R.drawable.vk_24,
        imageVector = null,
        contentDescription = R.string.reddit_content_desc,
        url = "https://vk.com/mahabbaa"
    ),

    Badge(
        id = "other_apps",
        drawable = R.drawable.ic_baseline_apps_24,
        imageVector = null,
        contentDescription = R.string.patreon_content_desc,
        url = "https://apps.rustore.ru/developer/ZPBnoCoBczpBFPZK0munW8NSpRTEayCj"
    ),

    Badge(
        id = "rate_app",
        drawable = R.drawable.ic_baseline_rate_review_24,
        imageVector = null,
        contentDescription = R.string.patreon_content_desc,
        url = "https://www.rustore.ru/catalog/app/raf.console.chitalka"
    ),

    Badge(
        id = "palestine",
        drawable = R.drawable.palestine_ard,
        imageVector = null,
        contentDescription = R.string.tryzub_content_desc,
        url = "Free Palestine!"
    ),
    Badge(
        id = "telegram",
        drawable = R.drawable.telegram_24,
        imageVector = null,
        contentDescription = R.string.patreon_content_desc,
        url = "https://t.me/ibnRustum"
    ),
    Badge(
        id = "github_profile",
        drawable = null,
        imageVector = Icons.Default.Person,
        contentDescription = R.string.github_profile_content_desc,
        url = "https://www.github.com/Raf0707"
    ),
)