package raf.console.chitalka.presentation.screens.help.components.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.presentation.screens.help.components.HelpAnnotation
import raf.console.chitalka.presentation.screens.help.components.HelpClickableNote

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