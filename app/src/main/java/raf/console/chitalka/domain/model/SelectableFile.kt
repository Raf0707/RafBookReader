package raf.console.chitalka.domain.model

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.util.Selected
import java.io.File

@Immutable
data class SelectableFile(
    val fileOrDirectory: File,
    val parentDirectory: File,
    val isDirectory: Boolean,
    val isFavorite: Boolean,
    val isSelected: Selected
)