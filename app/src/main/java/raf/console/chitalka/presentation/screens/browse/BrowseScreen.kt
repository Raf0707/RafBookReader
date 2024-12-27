package raf.console.chitalka.presentation.screens.browse

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import raf.console.chitalka.R
import raf.console.chitalka.domain.model.SelectableFile
import raf.console.chitalka.presentation.core.navigation.LocalNavigator
import raf.console.chitalka.presentation.core.navigation.Screen
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.presentation.data.MainViewModel
import raf.console.chitalka.presentation.screens.browse.components.BrowseEmptyPlaceholder
import raf.console.chitalka.presentation.screens.browse.components.BrowseStoragePermissionDialog
import raf.console.chitalka.presentation.screens.browse.components.adding_dialog.BrowseAddingDialog
import raf.console.chitalka.presentation.screens.browse.components.filter_bottom_sheet.BrowseFilterBottomSheet
import raf.console.chitalka.presentation.screens.browse.components.layout.BrowseLayout
import raf.console.chitalka.presentation.screens.browse.components.top_bar.BrowseTopBar
import raf.console.chitalka.presentation.screens.browse.data.BrowseEvent
import raf.console.chitalka.presentation.screens.browse.data.BrowseViewModel
import raf.console.chitalka.presentation.screens.settings.nested.browse.data.BrowseFilesStructure
import raf.console.chitalka.presentation.ui.DefaultTransition
import raf.console.chitalka.presentation.core.components.progress_indicator.CircularProgressIndicator

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BrowseScreenRoot() {
    val state = BrowseViewModel.getState()
    val mainState = MainViewModel.getState()
    val onEvent = BrowseViewModel.getEvent()

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val filteredFiles = remember(
        state.value.selectableFiles,
        state.value.selectedDirectory
    ) {
        derivedStateOf {
            BrowseViewModel.filterList(
                browseState = state.value,
                mainState = mainState.value
            )
        }
    }

    LaunchedEffect(Unit) {
        onEvent(BrowseEvent.OnUpdateScrollOffset)
        onEvent(BrowseEvent.OnPermissionCheck(permissionState))
    }

    BrowseScreen(
        permissionState = permissionState,
        filteredFiles = filteredFiles.value,
    )

    DisposableEffect(Unit) {
        onDispose {
            onEvent(BrowseEvent.OnClearViewModel)
        }
    }
}

@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalMaterialApi::class
)
@Composable
private fun BrowseScreen(
    permissionState: PermissionState,
    filteredFiles: List<SelectableFile>
) {
    val state = BrowseViewModel.getState()
    val mainState = MainViewModel.getState()
    val onEvent = BrowseViewModel.getEvent()
    val onNavigate = LocalNavigator.current
    val context = LocalContext.current

    val refreshState = rememberPullRefreshState(
        refreshing = state.value.isRefreshing,
        onRefresh = {
            onEvent(BrowseEvent.OnRefreshList)
        }
    )

    if (state.value.requestPermissionDialog) {
        BrowseStoragePermissionDialog(permissionState)
    }
    if (state.value.showAddingDialog) {
        BrowseAddingDialog()
    }
    if (state.value.showFilterBottomSheet) {
        BrowseFilterBottomSheet()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BrowseTopBar(filteredFiles = filteredFiles)
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(96.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .padding(6.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                DefaultTransition(visible = true) {
                    BrowseLayout(
                        filteredFiles = filteredFiles,
                        onLongItemClick = { selectableFile ->
                            when (selectableFile.isDirectory) {
                                false -> {
                                    context.getString(
                                        R.string.file_path_query,
                                        selectableFile.fileOrDirectory.path
                                    ).showToast(context = context)
                                }

                                true -> {
                                    onEvent(
                                        BrowseEvent.OnSelectFile(
                                            includedFileFormats = mainState.value.browseIncludedFilterItems,
                                            file = selectableFile
                                        )
                                    )
                                }
                            }
                        },
                        onFavoriteItemClick = {
                            onEvent(
                                BrowseEvent.OnUpdateFavoriteDirectory(
                                    it.fileOrDirectory.path
                                )
                            )
                        },
                        onItemClick = { selectableFile ->
                            when (selectableFile.isDirectory) {
                                false -> {
                                    onEvent(
                                        BrowseEvent.OnSelectFile(
                                            includedFileFormats = mainState.value.browseIncludedFilterItems,
                                            file = selectableFile
                                        )
                                    )
                                }

                                true -> {
                                    if (!state.value.hasSelectedItems) {
                                        onEvent(
                                            BrowseEvent.OnChangeDirectory(
                                                selectableFile.fileOrDirectory,
                                                savePreviousDirectory = true
                                            )
                                        )
                                    } else {
                                        onEvent(
                                            BrowseEvent.OnSelectFile(
                                                includedFileFormats = mainState.value.browseIncludedFilterItems,
                                                file = selectableFile
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }

            BrowseEmptyPlaceholder(
                isFilesEmpty = filteredFiles.isEmpty(),
                storagePermissionState = permissionState,
            )

            PullRefreshIndicator(
                state.value.isRefreshing,
                refreshState,
                Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    }

    BackHandler {
        if (state.value.hasSelectedItems) {
            onEvent(BrowseEvent.OnClearSelectedFiles)
            return@BackHandler
        }

        if (state.value.showSearch) {
            onEvent(BrowseEvent.OnSearchShowHide)
            return@BackHandler
        }

        if (
            state.value.inNestedDirectory
            && mainState.value.browseFilesStructure != BrowseFilesStructure.ALL_FILES
        ) {
            onEvent(BrowseEvent.OnGoBackDirectory)
            return@BackHandler
        }

        onNavigate {
            navigate(Screen.Library)
        }
    }
}