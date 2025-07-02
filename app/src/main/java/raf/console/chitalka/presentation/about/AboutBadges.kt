/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.about

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
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
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.constants.provideAboutBadges
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.ui.about.AboutEvent

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
                        /*"palestine" -> {
                            "Free Palestine!"
                                .showToast(context = context, longToast = false)
                        }*/
                        "shareApp" -> {
                            val appLink =
                                "https://play.google.com/store/apps/details?id=raf.console.chitalka"

                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Download RafBook Reader", "Download RafBook Reader \n" +
                                    "\n" +
                                    " $appLink")
                            clipboard.setPrimaryClip(clip)


                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, R.string.share_app)
                                putExtra(Intent.EXTRA_TEXT, "Download RafBook Reader \n\n $appLink")
                            }

                            try {
                                context.startActivity(
                                    Intent.createChooser(shareIntent, "Share app with")
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
                                putExtra(Intent.EXTRA_SUBJECT, "Feedback") // Тема письма
                                putExtra(Intent.EXTRA_TEXT, "Hello, I'm <your name>,\n\n") // Текст по умолчанию
                            }

                            try {
                                context.startActivity(Intent.createChooser(emailIntent, "Select a mail client"))
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    context,
                                    "Error: No email clients available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        /*"rate_app_here" -> {

                        }*/

                        /*"info_version" -> {
                            val activity = context as? Activity
                            if (activity != null) {
                                checkForUpdates(context, activity)
                            } else {
                                Log.e(TAG, "Ошибка: context не является Activity")
                            }
                        }*/

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
