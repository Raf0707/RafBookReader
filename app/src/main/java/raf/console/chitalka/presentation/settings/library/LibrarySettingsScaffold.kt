/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.library

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.domain.library.custom_category.Category
import raf.console.chitalka.presentation.navigator.NavigatorBackIconButton
import raf.console.chitalka.presentation.core.components.common.StyledText
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.math.Expr
import raf.console.chitalka.ui.settings.SettingsModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySettingsScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    listState: LazyListState,
    categories: List<Category>,
    navigateBack: () -> Unit,
    onCreate: (String) -> Unit,
    onToggleVisibility: (Int, Boolean) -> Unit,
    onRename: (Int, String) -> Unit,
    onDelete: (Int, Int?) -> Unit,
    settingsModel: SettingsModel
) {
    var isEditMode by remember { mutableStateOf(false) }

    Scaffold(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            LargeTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StyledText(stringResource(id = R.string.categories_settings_title))
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { isEditMode = !isEditMode }
                        ) {
                            Icon(
                                imageVector = if (isEditMode) Icons.Outlined.Check else Icons.Outlined.Edit,
                                contentDescription = if (isEditMode) "Confirm" else "Edit"
                            )
                        }
                    }
                },
                navigationIcon = { NavigatorBackIconButton(navigateBack = navigateBack) },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) { padding ->
        LibrarySettingsLayout(
            listState = listState,
            paddingValues = padding,
            categories = categories,
            onCreate = onCreate,
            onToggleVisibility = onToggleVisibility,
            onRename = onRename,
            onDelete = onDelete,
            onReorder = settingsModel::updateCategoryPositions,
            isEditMode = isEditMode // 👈 добавлено сюда
        )
    }
}