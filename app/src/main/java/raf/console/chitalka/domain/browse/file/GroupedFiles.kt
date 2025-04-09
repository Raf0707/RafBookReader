package raf.console.chitalka.domain.browse.file

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.browse.SelectableFile

@Immutable
data class GroupedFiles(
    val header: String,
    val pinned: Boolean,
    val files: List<SelectableFile>
)