/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.history

import raf.console.chitalka.domain.history.History
import raf.console.chitalka.domain.repository.HistoryRepository
import javax.inject.Inject

class GetLatestHistory @Inject constructor(
    private val repository: HistoryRepository
) {

    suspend fun execute(bookId: Int): History? {
        return repository.getLatestBookHistory(bookId)
    }
}