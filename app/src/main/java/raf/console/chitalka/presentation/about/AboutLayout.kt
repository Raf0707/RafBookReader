/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.about

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar
import raf.console.chitalka.presentation.core.constants.bugReportPage
import raf.console.chitalka.presentation.core.constants.provideContributorsPage
import raf.console.chitalka.presentation.core.constants.provideIssuesPage
import raf.console.chitalka.presentation.core.constants.provideReleasesPage
import raf.console.chitalka.presentation.core.constants.provideSupportPage
import raf.console.chitalka.presentation.core.constants.provideTranslationPage
import raf.console.chitalka.ui.about.AboutEvent

@Composable
fun AboutLayout(
    paddingValues: PaddingValues,
    listState: LazyListState,
    navigateToBrowserPage: (AboutEvent.OnNavigateToBrowserPage) -> Unit,
    navigateToLicenses: () -> Unit,
    navigateToCredits: () -> Unit
) {
    val context = LocalContext.current

    LazyColumnWithScrollbar(
        Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(id = R.drawable.raf_book_1),
                    contentDescription = stringResource(id = R.string.app_icon_content_desc),
                    modifier = Modifier
                        .padding(14.dp)
                        .size(120.dp)
                        .scale(1.5f)
                    //tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(36.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        item {
            AboutItem(
                title = stringResource(id = R.string.share_app),
                description = "RafBook v${stringResource(id = R.string.app_version)}",
            ) {
                // Создаем текст для шаринга
                val shareText = "Скачайте приложение RafBook по ссылке https://play.google.com/store/apps/details?id=raf.console.chitalka"

                // Копируем ссылку в буфер обмена
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("app_share_link", shareText)
                clipboard.setPrimaryClip(clip)

                // Создаем интент для шаринга
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }

                // Запускаем интент
                context.startActivity(Intent.createChooser(shareIntent, "Поделиться приложением"))
            }
        }

        item {
            AboutItem(
                title = stringResource(id = R.string.report_bug_option),
                description = null
            ) {
                navigateToBrowserPage(
                    AboutEvent.OnNavigateToBrowserPage(
                        page = bugReportPage(),
                        context = context
                    )
                )
            }
        }


        item {
            AboutItem(
                title = stringResource(id = R.string.contributors_option),
                description = null
            ) {
                navigateToBrowserPage(
                    AboutEvent.OnNavigateToBrowserPage(
                        page = provideContributorsPage(),
                        context = context
                    )
                )
            }
        }

        item {
            AboutItem(
                title = stringResource(id = R.string.licenses_option),
                description = null
            ) {
                navigateToLicenses()
            }
        }

        item {
            AboutItem(
                title = stringResource(id = R.string.credits_option),
                description = null
            ) {
                navigateToCredits()
            }
        }

        item {
            AboutItem(
                title = stringResource(id = R.string.help_translate_option),
                description = null
            ) {
                navigateToBrowserPage(
                    AboutEvent.OnNavigateToBrowserPage(
                        page = provideTranslationPage(),
                        context = context
                    )
                )
            }
        }

        item {
            AboutItem(
                title = stringResource(id = R.string.support_development_option),
                description = null
            ) {
                navigateToBrowserPage(
                    AboutEvent.OnNavigateToBrowserPage(
                        page = provideSupportPage(),
                        context = context
                    )
                )
            }
        }

        item {
            AboutBadges(
                navigateToBrowserPage = navigateToBrowserPage
            )
        }
    }
}