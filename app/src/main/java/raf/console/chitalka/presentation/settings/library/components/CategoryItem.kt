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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.custom_category.Category


/*@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(
    category: Category,
    onToggleVisibility: () -> Unit,
    onEdit: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (category.isDefault) 1.dp else 2.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (category.isDefault) {
                MaterialTheme.colorScheme.surfaceContainerLowest
            } else {
                MaterialTheme.colorScheme.surfaceContainerLowest
            }
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(text = category.title.asString())
            },
            supportingContent = {
                if (category.isDefault) {
                    Text(text = stringResource(id = R.string.default_category_label))
                }
            },
            leadingContent = {
                CategoryIcon(category.isDefault)
            },
            trailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    VisibilityButton(category.isVisible, onToggleVisibility)
                    if (!category.isDefault) {
                        SmallIconButton(Icons.Outlined.Edit, R.string.edit_category, onEdit)
                    }
                }
            }
        )
    }
}

@Composable
private fun CategoryIcon(isDefault: Boolean) {
    androidx.compose.material3.Surface(
        shape = CircleShape,
        color = if (isDefault) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.size(48.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Outlined.Label,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isDefault) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun VisibilityButton(visible: Boolean, onClick: () -> Unit) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = if (visible) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (visible) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Icon(
            imageVector = if (visible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
            contentDescription = if (visible) stringResource(id = R.string.hide_category) else stringResource(id = R.string.show_category),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SmallIconButton(icon: ImageVector, descRes: Int, onClick: () -> Unit, isError: Boolean = false) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Icon(imageVector = icon, contentDescription = stringResource(id = descRes), modifier = Modifier.size(20.dp))
    }
}*/



/*---------------------*/


/*@Composable
fun CategoryItem(
    category: Category,
    onToggleVisibility: () -> Unit,
    onEdit: () -> Unit,
    onDelete: (() -> Unit)? = null,
    dragHandle: (@Composable () -> Unit)? = null,
    isDragging: Boolean = false
) {
    val dragAlpha by animateFloatAsState(
        targetValue = if (isDragging) 0.5f else 1f,
        label = "dragAlpha"
    )

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

                dragHandle?.invoke()
            }


            SmallIconButton(
                icon = if (category.isVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDesc = if (category.isVisible) R.string.hide_category else R.string.show_category,
                onClick = onToggleVisibility
            )

            SmallIconButton(
                icon = Icons.Outlined.Edit,
                contentDesc = R.string.edit_category,
                onClick = onEdit
            )

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
}*/

@Composable
fun CategoryItem(
    category: Category,
    onToggleVisibility: () -> Unit,
    onEdit: () -> Unit,
    onDelete: (() -> Unit)? = null,
    dragHandle: (@Composable () -> Unit)? = null,
    isDragging: Boolean = false,
    isEditMode: Boolean
) {
    val dragAlpha by animateFloatAsState(
        targetValue = if (isDragging) 0.5f else 1f,
        label = "dragAlpha"
    )

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
