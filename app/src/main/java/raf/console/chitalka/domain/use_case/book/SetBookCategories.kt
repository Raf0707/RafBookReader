/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class SetBookCategories @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookId: Int, categoryIds: List<Int>) {
        repository.setCategories(bookId, categoryIds)
    }
} 