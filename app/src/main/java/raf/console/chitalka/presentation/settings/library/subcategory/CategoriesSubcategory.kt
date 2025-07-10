/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.library.subcategory

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.custom_category.Category
import raf.console.chitalka.presentation.settings.components.SettingsSubcategory
import raf.console.chitalka.presentation.settings.library.CategoryDeleteDialog
import raf.console.chitalka.presentation.settings.library.components.CategoryItem
import raf.console.chitalka.presentation.settings.library.components.CreateCategoryButton
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin


@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.CategoriesSubcategory(
    categories: List<Category>,
    onToggleVisibility: (Int, Boolean) -> Unit,
    onRequestRename: (Int, String) -> Unit,
    onDelete: (Int, Int?) -> Unit,
    onRequestCreate: () -> Unit,
    isEditMode: Boolean // 👈 добавлено
) {
    item { Spacer(Modifier.height(4.dp)) }

    SettingsSubcategory(
        titleColor = { MaterialTheme.colorScheme.primary },
        title = { "" },
        showTitle = false,
        showDivider = false
    ) {
        categories.forEach { cat ->
            item(key = cat.id) {
                val scope = rememberCoroutineScope()
                var askDelete by remember { mutableStateOf(false) }

                CategoryItem(
                    category = cat,
                    onToggleVisibility = { onToggleVisibility(cat.id, !cat.isVisible) },
                    onEdit = { onRequestRename(cat.id, cat.name) },
                    onDelete = if (!cat.isDefault && isEditMode) {
                        { askDelete = true }
                    } else null,
                    isDragging = false,
                    isEditMode = isEditMode,
                    dragHandle = if (isEditMode) {
                        {
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .size(36.dp)
                                    .alpha(0.6f) // можно убрать если не нужно визуально отличать
                            ) {}
                        }
                    } else null,
                    dragModifier = Modifier
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
                CreateCategoryButton {
                    onRequestCreate()
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        item {
            Text(
                text = stringResource(id = R.string.categories_settings_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}


