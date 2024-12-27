package raf.console.chitalka.domain.model

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.util.UIText

@Immutable
data class Credit(
    val name: String,
    val source: String?,
    val credits: List<UIText>,
    val website: String?
)
