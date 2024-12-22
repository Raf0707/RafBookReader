package com.byteflipper.everbook.presentation.screens.browse.components.layout.grid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.byteflipper.everbook.domain.model.SelectableFile
import com.byteflipper.everbook.presentation.core.components.common.header
import com.byteflipper.everbook.presentation.screens.browse.components.layout.BrowseItem
import com.byteflipper.everbook.presentation.screens.browse.data.BrowseViewModel
import com.byteflipper.everbook.presentation.screens.settings.nested.browse.data.BrowseLayout

@Composable
fun BrowseGridLayout(
    gridSize: Int,
    autoGridSize: Boolean,
    filteredFiles: List<SelectableFile>,
    onLongItemClick: (SelectableFile) -> Unit,
    onFavoriteItemClick: (SelectableFile) -> Unit,
    onItemClick: (SelectableFile) -> Unit,
) {
    val state = BrowseViewModel.getState()

    LazyVerticalGrid(
        columns = if (autoGridSize) GridCells.Adaptive(170.dp)
        else GridCells.Fixed(gridSize.coerceAtLeast(1)),
        state = state.value.gridState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        header {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(
            filteredFiles,
            key = { it.fileOrDirectory.path }
        ) { selectableFile ->
            BrowseItem(
                file = selectableFile,
                modifier = Modifier.animateItem(),
                layout = BrowseLayout.GRID,
                hasSelectedFiles = state.value.selectableFiles.any { it.isSelected },
                onLongClick = {
                    onLongItemClick(selectableFile)
                },
                onFavoriteClick = {
                    onFavoriteItemClick(selectableFile)
                },
                onClick = {
                    onItemClick(selectableFile)
                }
            )
        }

        header {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}