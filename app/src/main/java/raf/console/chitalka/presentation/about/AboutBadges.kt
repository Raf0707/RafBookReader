/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.about

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import raf.console.chitalka.Activity
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.constants.provideAboutBadges
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.ui.about.AboutEvent
import raf.console.chitalka.util.update.checkForUpdates
import ru.rustore.sdk.review.RuStoreReviewManagerFactory

@Composable
fun AboutBadges(
    navigateToBrowserPage: (AboutEvent.OnNavigateToBrowserPage) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(
                provideAboutBadges(),
                key = { it.id }
            ) { badge ->
                AboutBadgeItem(badge = badge) {
                    when (badge.id) {
                        "palestine" -> {
                            "Free Palestine!"
                                .showToast(context = context, longToast = false)
                        }
                        "shareApp" -> {
                            val appLink =
                                "https://www.rustore.ru/catalog/app/raf.console.chitalka"
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Поделитесь этим приложением")
                                putExtra(Intent.EXTRA_TEXT, "Скачайте приложение «RafBook» по этой ссылке: $appLink")
                            }

                            try {
                                context.startActivity(
                                    Intent.createChooser(shareIntent, "Поделиться приложением через")
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    context,
                                    "Ошибка при попытке поделиться",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        "email" -> {
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:") // Только email-клиенты
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("raf_android-dev@mail.ru")) // Адрес
                                putExtra(Intent.EXTRA_SUBJECT, "Обратная связь") // Тема письма
                                putExtra(Intent.EXTRA_TEXT, "Здравствуйте,\n\n") // Текст по умолчанию
                            }

                            try {
                                context.startActivity(Intent.createChooser(emailIntent, "Выберите почтовый клиент"))
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    context,
                                    "Ошибка: нет доступных почтовых клиентов",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        "rate_app_here" -> {
                            val manager = RuStoreReviewManagerFactory.create(context)

                            manager.requestReviewFlow()
                                .addOnSuccessListener { reviewInfo ->
                                    manager.launchReviewFlow(reviewInfo)
                                        .addOnFailureListener {
                                            // Если RuStore не позволяет открыть окно повторно → открываем страницу приложения
                                            openRuStorePage(context)
                                        }
                                }
                                .addOnFailureListener {
                                    // Ошибка при запросе ReviewInfo → сразу открываем страницу приложения
                                    openRuStorePage(context)
                                }
                        }

                        "info_version" -> {
                            val activity = context as? Activity
                            if (activity != null) {
                                checkForUpdates(context, activity)
                            } else {
                                Log.e(TAG, "Ошибка: context не является Activity")
                            }
                        }

                        else -> {
                            badge.url?.let {
                                navigateToBrowserPage(
                                    AboutEvent.OnNavigateToBrowserPage(
                                        page = it,
                                        context = context
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


fun openRuStorePage(context: Context) {
    val uri = Uri.parse("https://www.rustore.ru/catalog/app/raf.console.chitalka")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("ru.rustore")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Если RuStore не установлен, открываем страницу в браузере
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}