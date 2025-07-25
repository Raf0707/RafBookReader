/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.category

import raf.console.chitalka.domain.repository.CategoryRepository
import javax.inject.Inject

class ReorderCategories @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(order: List<Int>) {
        repository.reorderCategories(order)
    }
} 