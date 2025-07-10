/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.library.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.custom_category.Category
import sh.calvin.reorderable.ReorderableCollectionItemScope
import kotlin.math.truncate

@Composable
fun ReorderableCollectionItemScope.DraggableCategoryItem(
    category: Category,
    onToggleVisibility: () -> Unit,
    onEdit: () -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    CategoryItem(
        category = category,
        onToggleVisibility = onToggleVisibility,
        onEdit = onEdit,
        onDelete = {},
        isDragging = true,
        isEditMode = true,
        dragModifier = Modifier.longPressDraggableHandle(
            onDragStarted = {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            },
            onDragStopped = {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
        )
    )
}

@Composable
fun CategoryItem(
    category: Category,
    onToggleVisibility: () -> Unit,
    onEdit: () -> Unit,
    onDelete: (() -> Unit)? = null,
    dragHandle: (@Composable () -> Unit)? = null,
    isDragging: Boolean = false,
    isEditMode: Boolean,
    dragModifier: Modifier = Modifier,
) {
    val dragAlpha by animateFloatAsState(
        targetValue = if (isDragging) 0.5f else 1f,
        label = "dragAlpha"
    )

    //var dragHandle = DragHandle(dragModifier)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .alpha(dragAlpha),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocalOffer,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .graphicsLayer { scaleX = -1f },
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(
                text = category.title.asString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            if (isEditMode) {
                IconButton(
                    onClick = {}, // не нужен onClick — перетаскивание по удержанию
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Dehaze,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    //dragHandle?.invoke()
                    dragHandle?.invoke()
                }
            }

            SmallIconButton(
                icon = if (category.isVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDesc = if (category.isVisible) R.string.hide_category else R.string.show_category,
                onClick = onToggleVisibility
            )

            if (isEditMode) {
                onDelete?.let {
                    SmallIconButton(
                        icon = Icons.Outlined.Edit,
                        contentDesc = R.string.edit_category,
                        onClick = onEdit
                    )
                }

                onDelete?.let {
                    SmallIconButton(
                        icon = Icons.Outlined.Close,
                        contentDesc = R.string.delete_category,
                        onClick = it,
                        isError = true
                    )
                }
            }
        }
    }
}

/*@Composable
private fun DragHandle(modifier: Modifier = Modifier) {
    FilledTonalIconButton(
        onClick = { /* будет перехвачено draggableHandle модификатором */ },
        modifier = modifier
            .size(40.dp)
            .clearAndSetSemantics { },
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.DragIndicator,
            contentDescription = stringResource(id = R.string.drag_content_desc),
            modifier = Modifier.size(20.dp)
        )
    }
}*/


@Composable
private fun SmallIconButton(
    icon: ImageVector,
    contentDesc: Int,
    onClick: () -> Unit,
    isError: Boolean = false
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = contentDesc),
            modifier = Modifier.size(20.dp),
            tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
