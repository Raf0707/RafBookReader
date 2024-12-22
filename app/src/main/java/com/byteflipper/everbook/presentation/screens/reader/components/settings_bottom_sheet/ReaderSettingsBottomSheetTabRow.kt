package com.byteflipper.everbook.presentation.screens.reader.components.settings_bottom_sheet

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.modal_bottom_sheet.ModalBottomSheetTabRow
import com.byteflipper.everbook.presentation.screens.reader.data.ReaderEvent
import com.byteflipper.everbook.presentation.screens.reader.data.ReaderViewModel

/**
 * Settings bottom sheet tab row.
 * It is used to switch between categories.
 *
 * @param pagerState PagerState to use along with TabRow.
 */
@Composable
fun ReaderSettingsBottomSheetTabRow(pagerState: PagerState) {
    val onEvent = ReaderViewModel.getEvent()
    val tabItems = listOf(
        stringResource(id = R.string.general_tab),
        stringResource(id = R.string.reader_tab),
        stringResource(id = R.string.color_tab)
    )

    ModalBottomSheetTabRow(
        selectedTabIndex = pagerState.currentPage,
        tabs = tabItems
    ) { index ->
        onEvent(ReaderEvent.OnScrollToSettingsPage(index, pagerState))
    }
}