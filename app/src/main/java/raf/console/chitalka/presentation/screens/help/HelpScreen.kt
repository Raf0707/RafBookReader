package raf.console.chitalka.presentation.screens.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.util.Position
import raf.console.chitalka.presentation.core.components.common.GoBackButton
import raf.console.chitalka.presentation.core.components.common.IconButton
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar
import raf.console.chitalka.presentation.core.components.top_bar.collapsibleTopAppBarScrollBehavior
import raf.console.chitalka.presentation.core.constants.Constants
import raf.console.chitalka.presentation.core.constants.provideHelpTips
import raf.console.chitalka.presentation.core.navigation.LocalNavigator
import raf.console.chitalka.presentation.core.navigation.Screen
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel
import raf.console.chitalka.presentation.screens.browse.data.BrowseEvent
import raf.console.chitalka.presentation.screens.browse.data.BrowseViewModel
import raf.console.chitalka.presentation.screens.help.components.HelpItem
import raf.console.chitalka.presentation.screens.help.components.items.HelpClickMeNoteItem
import raf.console.chitalka.presentation.screens.help.data.HelpEvent
import raf.console.chitalka.presentation.screens.help.data.HelpViewModel
import raf.console.chitalka.presentation.screens.start.data.StartEvent
import raf.console.chitalka.presentation.screens.start.data.StartViewModel

@Composable
fun HelpScreenRoot(screen: Screen.Help) {
    val onEvent = HelpViewModel.getEvent()

    LaunchedEffect(Unit) {
        onEvent(
            HelpEvent.OnInit(
                screen = screen
            )
        )
    }

    HelpScreen()
}

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
private fun HelpScreen() {
    val state = HelpViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()
    val onBrowseEvent = BrowseViewModel.getEvent()
    val onStartEvent = StartViewModel.getEvent()
    val onNavigate = LocalNavigator.current

    val (scrollBehavior, lazyListState) = TopAppBarDefaults.collapsibleTopAppBarScrollBehavior()

    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(stringResource(id = R.string.help_screen))
                },
                navigationIcon = {
                    if (!state.value.fromStart) {
                        GoBackButton(onNavigate = onNavigate)
                    }
                },
                actions = {
                    if (!state.value.fromStart) {
                        IconButton(
                            icon = Icons.Outlined.RestartAlt,
                            contentDescription = R.string.reset_start_content_desc,
                            disableOnClick = false
                        ) {
                            onStartEvent(StartEvent.OnResetStartScreen)
                            onMainEvent(MainEvent.OnChangeShowStartScreen(true))
                            onNavigate {
                                navigate(Screen.Start, saveInBackStack = false)
                                clearBackStack()
                            }
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        bottomBar = {
            if (state.value.fromStart) {
                Column {
                    Spacer(modifier = Modifier.height(18.dp))
                    Button(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(bottom = 8.dp)
                            .padding(horizontal = 18.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(100),
                        onClick = {
                            onBrowseEvent(BrowseEvent.OnLoadList)
                            onMainEvent(MainEvent.OnChangeShowStartScreen(false))
                            onNavigate {
                                navigate(Screen.Browse, saveInBackStack = false)
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.done))
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumnWithScrollbar(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = lazyListState
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                HelpClickMeNoteItem()
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(
                Constants.provideHelpTips(),
                key = { _, helpTip -> helpTip.title }
            ) { index, helpTip ->
                HelpItem(
                    helpTip = helpTip,
                    position = when (index) {
                        0 -> Position.TOP
                        Constants.provideHelpTips().lastIndex -> Position.BOTTOM
                        else -> Position.CENTER
                    },
                    fromStart = state.value.fromStart
                )
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}