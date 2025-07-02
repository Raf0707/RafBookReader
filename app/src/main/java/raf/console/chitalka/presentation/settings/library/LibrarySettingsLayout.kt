/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.library

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import raf.console.chitalka.domain.library.custom_category.Category
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import raf.console.chitalka.presentation.core.components.dialog.DialogWithTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.settings.library.components.CategoryItem
import raf.console.chitalka.presentation.settings.library.components.CreateCategoryButton
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

/*@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibrarySettingsLayout(
    listState: LazyListState,
    paddingValues: PaddingValues,
    categories: List<Category>,
    onCreate: (String) -> Unit,
    onToggleVisibility: (Int, Boolean) -> Unit,
    onRename: (Int, String) -> Unit,
    onDelete: (Int, Int?) -> Unit,
    onReorder: (List<Category>) -> Unit,
    isEditMode: Boolean
) {
    var dialogData by remember { mutableStateOf<Pair<Int?, String>?>(null) }
    val categoryState = remember { mutableStateListOf<Category>() }

    LaunchedEffect(Unit) {
        categoryState.clear()
        categoryState.addAll(categories)
    }
    LaunchedEffect(categories) {
        categoryState.clear()
        categoryState.addAll(categories)
    }

    // 1. Создаём state для reorder
    val reorderState = rememberReorderableLazyListState(
        lazyListState = listState,
        onMove = {
                from, to -> categoryState.move(from.index, to.index)
            onReorder(categoryState)
        },

        // 👈 сохраняем порядок

    )

    if (dialogData != null) {
        DialogWithTextField(
            initialValue = dialogData!!.second,
            onDismiss = { dialogData = null },
            onAction = { newName ->
                val id = dialogData!!.first
                if (id == null) onCreate(newName) else onRename(id, newName)
                dialogData = null
            }
        )
    }

    LazyColumnWithScrollbar(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        //.reorderable(reorderState) // <-- enable reorder
        //.detectReorderAfterLongPress(reorderState), // <-- long‑press start
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categoryState, key = { it.id }) { cat ->
            ReorderableItem(reorderState, key = cat.id) { isDragging ->
                var askDelete by remember { mutableStateOf(false) }



                CategoryItem(
                    category = cat,
                    onToggleVisibility = { onToggleVisibility(cat.id, !cat.isVisible) },
                    onEdit = { dialogData = Pair(cat.id, cat.name) },
                    onDelete = if (cat.isDefault) null else {
                        { askDelete = true }
                    },
                    isDragging = isDragging,
                    dragHandle = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .size(36.dp)
                                .draggableHandle() // <-- handle modifier
                        ) {
                            /*Icon(
                                imageVector = Icons.Outlined.Dehaze,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )*/
                        }
                    }

                )

                if (askDelete) {
                    CategoryDeleteDialog(
                        onConfirm = {
                            askDelete = false
                            onDelete(cat.id, null)
                        },
                        onDismiss = {
                            askDelete = false
                        }
                    )
                }

                Spacer(Modifier.height(12.dp))
            }

        }

        item {
            Spacer(Modifier.height(8.dp))
            CreateCategoryButton { dialogData = Pair(null, "") }
            Spacer(Modifier.height(8.dp))
        }

        item {
            Text(
                text = stringResource(R.string.categories_settings_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}*/

fun <T> MutableList<T>.move(from: Int, to: Int) {
    if (from == to) return
    val item = removeAt(from)
    add(if (to > from) to - 1 else to, item)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibrarySettingsLayout(
    listState: LazyListState,
    paddingValues: PaddingValues,
    categories: List<Category>,
    onCreate: (String) -> Unit,
    onToggleVisibility: (Int, Boolean) -> Unit,
    onRename: (Int, String) -> Unit,
    onDelete: (Int, Int?) -> Unit,
    onReorder: (List<Category>) -> Unit,
    isEditMode: Boolean
) {
    var dialogData by remember { mutableStateOf<Pair<Int?, String>?>(null) }
    val categoryState = remember { mutableStateListOf<Category>() }

    LaunchedEffect(Unit) {
        categoryState.clear()
        categoryState.addAll(categories)
    }
    LaunchedEffect(categories) {
        categoryState.clear()
        categoryState.addAll(categories)
    }

    val reorderState = rememberReorderableLazyListState(
        lazyListState = listState,
        onMove = { from, to ->
            categoryState.move(from.index, to.index)
            onReorder(categoryState)
        }
    )

    if (dialogData != null) {
        DialogWithTextField(
            initialValue = dialogData!!.second,
            onDismiss = { dialogData = null },
            onAction = { newName ->
                val id = dialogData!!.first
                if (id == null) onCreate(newName) else onRename(id, newName)
                dialogData = null
            }
        )
    }

    LazyColumnWithScrollbar(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categoryState, key = { it.id }) { cat ->
            ReorderableItem(reorderState, key = cat.id) { isDragging ->
                var askDelete by remember { mutableStateOf(false) }

                CategoryItem(
                    category = cat,
                    onToggleVisibility = { onToggleVisibility(cat.id, !cat.isVisible) },
                    onEdit = { dialogData = Pair(cat.id, cat.name) },
                    onDelete = if (!cat.isDefault && isEditMode) {
                        { askDelete = true }
                    } else null,
                    isDragging = isDragging,
                    isEditMode = isEditMode,
                    dragHandle = if (isEditMode) {
                        {
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .size(36.dp)
                                    .draggableHandle()
                            ) {}
                        }
                    } else null
                )

                if (askDelete) {
                    CategoryDeleteDialog(
                        onConfirm = {
                            askDelete = false
                            onDelete(cat.id, null)
                        },
                        onDismiss = {
                            askDelete = false
                        }
                    )
                }

                Spacer(Modifier.height(12.dp))
            }
        }

        item {
            Spacer(Modifier.height(8.dp))
            if (isEditMode) {
                CreateCategoryButton { dialogData = Pair(null, "") }
                Spacer(Modifier.height(8.dp))
            }
        }

        item {
            Text(
                text = stringResource(R.string.categories_settings_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}


