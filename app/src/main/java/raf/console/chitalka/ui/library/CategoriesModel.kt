/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import raf.console.chitalka.domain.library.custom_category.Category
import raf.console.chitalka.domain.use_case.category.ObserveCategories
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoriesModel @Inject constructor(
    observeCategories: ObserveCategories
) : ViewModel() {
    val categories: Flow<List<Category>> = observeCategories().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
} 