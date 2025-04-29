/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.crash

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.R
import raf.console.chitalka.crash.CrashUtils
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.presentation.core.util.LocalActivity
import raf.console.chitalka.presentation.core.util.launchActivity
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.presentation.crash.CrashContent

@Parcelize
object CrashScreen : Screen, Parcelable {

    @Composable
    override fun Content() {
        val activity = LocalActivity.current
        val clipboard = LocalClipboard.current
        val scope = rememberCoroutineScope()

        val crashLog = remember {
            CrashUtils.getCrashLog(activity)?.trimIndent()
        }

        CrashContent(
            crashLog = crashLog,
            copy = {
                if (crashLog.isNullOrBlank()) return@CrashContent
                scope.launch(Dispatchers.Main) {
                    clipboard.setClipEntry(
                        ClipEntry(ClipData.newPlainText("Crash", crashLog))
                    )

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                        activity.getString(R.string.copied)
                            .showToast(context = activity, longToast = false)
                    }
                }
            },
            report = {
                try {
                    val textToSend = "```\n$crashLog\n```"

                    // Пробуем открыть Telegram через ACTION_SEND
                    val telegramIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, textToSend)
                        setPackage("org.telegram.messenger")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }

                    if (telegramIntent.resolveActivity(activity.packageManager) != null) {
                        activity.startActivity(telegramIntent)
                    } else {
                        // Fallback: копируем в буфер и открываем ссылку
                        val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Crash Report", textToSend)
                        clipboard.setPrimaryClip(clip)

                        activity.startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://t.me/rafbook_reader/2")
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })

                        activity.getString(R.string.copied)
                            .showToast(context = activity, longToast = false)
                    }

                    return@CrashContent
                } catch (e: Exception) {
                    activity.getString(R.string.error_no_share_app)
                        .showToast(context = activity, longToast = false)
                }
            }
        )
    }
}