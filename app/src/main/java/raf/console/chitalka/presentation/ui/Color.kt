package raf.console.chitalka.presentation.ui

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import raf.console.chitalka.R
import raf.console.chitalka.presentation.ui.theme.aquaTheme
import raf.console.chitalka.presentation.ui.theme.blackTheme
import raf.console.chitalka.presentation.ui.theme.blueTheme
import raf.console.chitalka.presentation.ui.theme.dynamicTheme
import raf.console.chitalka.presentation.ui.theme.grayTheme
import raf.console.chitalka.presentation.ui.theme.greenTheme
import raf.console.chitalka.presentation.ui.theme.lavenderTheme
import raf.console.chitalka.presentation.ui.theme.marshTheme
import raf.console.chitalka.presentation.ui.theme.pink2Theme
import raf.console.chitalka.presentation.ui.theme.pinkTheme
import raf.console.chitalka.presentation.ui.theme.purpleTheme
import raf.console.chitalka.presentation.ui.theme.redTheme
import raf.console.chitalka.presentation.ui.theme.yellowTheme


@Immutable
enum class Theme(
    val hasThemeContrast: Boolean,
    @StringRes val title: Int
) {
    DYNAMIC(hasThemeContrast = false, title = R.string.dynamic_theme),
    BLUE(hasThemeContrast = true, title = R.string.blue_theme),
    GRAY(hasThemeContrast = false, title = R.string.gray_theme),
    GREEN(hasThemeContrast = true, title = R.string.green_theme),
    MARSH(hasThemeContrast = true, title = R.string.marsh_theme),
    RED(hasThemeContrast = true, title = R.string.red_theme),
    PURPLE(hasThemeContrast = true, title = R.string.purple_theme),
    LAVENDER(hasThemeContrast = true, title = R.string.lavender_theme),
    PINK(hasThemeContrast = true, title = R.string.pink_theme),
    PINK2(hasThemeContrast = false, title = R.string.pink2_theme),
    YELLOW(hasThemeContrast = true, title = R.string.yellow_theme),
    AQUA(hasThemeContrast = true, title = R.string.aqua_theme);

    companion object {
        fun entries(): List<Theme> {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> Theme.entries
                else -> Theme.entries.dropWhile { it == DYNAMIC }
            }
        }
    }
}

/**
 * Converting [String] into [Theme].
 */
fun String.toTheme(): Theme {
    return Theme.valueOf(this)
}

/**
 * Creates a colorscheme based on [Theme].
 *
 * @param theme a [Theme].
 *
 * @return a [ColorScheme].
 */
@Composable
fun colorScheme(
    theme: Theme,
    darkTheme: Boolean,
    isPureDark: Boolean,
    themeContrast: ThemeContrast
): ColorScheme {
    val colorScheme = when (theme) {
        Theme.DYNAMIC -> {
            /* Dynamic Theme */
            dynamicTheme(isDark = darkTheme)
        }

        Theme.BLUE -> {
            /* Blue Theme */
            blueTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PURPLE -> {
            /* Purple Theme */
            purpleTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.GREEN -> {
            /* Green Theme */
            greenTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.MARSH -> {
            /* Marsh Theme */
            marshTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PINK -> {
            /* Pink Theme */
            pinkTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.PINK2 -> {
            /* Pink2 Theme */
            pink2Theme(isDark = darkTheme)
        }

        Theme.LAVENDER -> {
            /* Lavender Theme */
            lavenderTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.YELLOW -> {
            /* Yellow Theme */
            yellowTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.RED -> {
            /* Red Theme */
            redTheme(isDark = darkTheme, themeContrast = themeContrast)
        }

        Theme.GRAY -> {
            /* Gray Theme */
            grayTheme(isDark = darkTheme)
        }

        Theme.AQUA -> {
            /* Aqua Theme */
            aquaTheme(isDark = darkTheme, themeContrast = themeContrast)
        }
    }

    return if (isPureDark && darkTheme) {
        blackTheme(initialTheme = colorScheme)
    } else {
        colorScheme
    }
}