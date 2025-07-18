/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.util

import android.annotation.SuppressLint
import android.content.Intent
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext

inline fun Intent.launchActivity(
    activity: ComponentActivity,
    createChooser: Boolean = false,
    openInNewWindow: Boolean = true,
    success: () -> Unit = {},
    error: () -> Unit = {}
) {
    try {
        val intent = if (createChooser) Intent.createChooser(this, "") else this
        if (openInNewWindow) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        activity.baseContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        error()
        return
    }

    success()
}

fun ComponentActivity.setBrightness(brightness: Float?) {
    window.attributes.apply {
        screenBrightness = brightness ?: WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = this
    }
}

val LocalActivity: ProvidableCompositionLocal<ComponentActivity>
    @SuppressLint("ContextCastToActivity") @Composable get() {
        (LocalContext.current as ComponentActivity).let { activity ->
            return compositionLocalOf { activity }
        }
    }