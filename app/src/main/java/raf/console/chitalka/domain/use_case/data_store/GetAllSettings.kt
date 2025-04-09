/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.data_store

import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.ui.main.MainState
import javax.inject.Inject

class GetAllSettings @Inject constructor(
    private val repository: DataStoreRepository
) {

    suspend fun execute(): MainState {
        return repository.getAllSettings()
    }
}