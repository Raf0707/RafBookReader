package raf.console.chitalka.presentation.reader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.util.noRippleClickable

@Composable
fun SelectableParagraph(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    onDoubleClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    toolbarHidden: Boolean = true,
    highlightText: Boolean = false,
    highlightThickness: FontWeight = FontWeight.Normal,
    openTranslator: ((String) -> Unit)? = null
) {
    androidx.compose.foundation.text.selection.SelectionContainer {
        StyledText(
            text = text,
            modifier = modifier.then(
                if (onDoubleClick != null && toolbarHidden) {
                    Modifier.noRippleClickable(
                        onDoubleClick = {
                            onDoubleClick()
                        },
                        onClick = {
                            onClick?.invoke()
                        }
                    )
                } else Modifier
            ),
            style = style,
            highlightText = highlightText,
            highlightThickness = highlightThickness
        )
    }
}

/*@Composable
fun SelectableParagraph(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    onDoubleClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    toolbarHidden: Boolean = true,
    highlightText: Boolean = false,
    highlightThickness: FontWeight = FontWeight.Normal
) {
    StyledText(
        text = text,
        modifier = modifier.then(
            if (onDoubleClick != null && toolbarHidden) {
                Modifier.noRippleClickable(
                    onDoubleClick = { onDoubleClick() },
                    onClick = { onClick?.invoke() }
                )
            } else Modifier
        ),
        style = style,
        highlightText = highlightText,
        highlightThickness = highlightThickness
    )
}*/

