/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.data_store

import androidx.datastore.preferences.core.Preferences
import raf.console.chitalka.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetDatastore @Inject constructor(
    private val repository: DataStoreRepository
) {

    suspend fun <T> execute(key: Preferences.Key<T>, value: T) {
        repository.putDataToDataStore(key, value)
    }
}