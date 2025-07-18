/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.core.constants

import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.domain.help.HelpTip
import raf.console.chitalka.presentation.help.HelpAnnotation

fun provideHelpTips() = listOf(
    HelpTip(
        title = R.string.help_title_how_to_add_books,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_add_books_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_add_books_2))
            }
            append(". ")

            append(stringResource(id = R.string.help_desc_how_to_add_books_3) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_add_books_4))
            }
            append(".")
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_customize_app,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_customize_app_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_customize_app_2))
            }
            append(". ")

            append(stringResource(id = R.string.help_desc_how_to_customize_app_3))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_move_or_delete_books,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_move_or_delete_books_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_move_or_delete_books_2))
            }
            append(". ")

            append(stringResource(id = R.string.help_desc_how_to_move_or_delete_books_3))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_edit_book,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_edit_book_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_edit_book_2))
            }
            append(" ")

            append(stringResource(id = R.string.help_desc_how_to_edit_book_3))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_read_book,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_read_book_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_read_book_2))
            }
            append(". ")

            append(stringResource(id = R.string.help_desc_how_to_read_book_3))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_customize_reader,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_customize_reader_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_customize_reader_2))
            }
            append(" ")

            append(stringResource(id = R.string.help_desc_how_to_customize_reader_3))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_manage_history,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_manage_history_1) + " ")

            HelpAnnotation {
                append(stringResource(id = R.string.help_desc_how_to_manage_history_2))
            }
            append(". ")

            append(stringResource(id = R.string.help_desc_how_to_manage_history_3))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_use_tooltip,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_use_tooltip_1))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_use_double_click_translation,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_use_double_click_translation_1))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_create_color_presets,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_create_color_presets_1))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_use_color_presets,
        description = {
            append(stringResource(id = R.string.help_desc_how_to_use_color_presets_1))
        }
    ),

    HelpTip(
        title = R.string.help_title_how_to_use_perception_expander,
        description = {
            append(stringResource(id = R.string.help_title_how_to_use_perception_expander_1))
        }
    ),
)