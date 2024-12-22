package com.byteflipper.everbook.presentation.screens.help.components.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.util.showToast
import com.byteflipper.everbook.presentation.screens.help.components.HelpAnnotation
import com.byteflipper.everbook.presentation.screens.help.components.HelpClickableNote

/**
 * Help Click Me Note Item.
 * Shows an example to user on how to interact with highlighted text.
 */
@Composable
fun HelpClickMeNoteItem() {
    val context = LocalContext.current

    HelpClickableNote(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.help_clickable_note_1) + " ")

            HelpAnnotation(
                onClick = {
                    context.getString(R.string.help_clickable_note_action)
                        .showToast(context = context, longToast = false)
                }
            ) {
                append(stringResource(id = R.string.help_clickable_note_2))
            }

            append(" " + stringResource(id = R.string.help_clickable_note_3))
        }
    )
}