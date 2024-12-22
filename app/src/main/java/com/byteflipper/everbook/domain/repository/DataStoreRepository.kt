package com.byteflipper.everbook.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.byteflipper.everbook.presentation.data.MainState

interface DataStoreRepository {

    suspend fun <T> putDataToDataStore(
        key: Preferences.Key<T>,
        value: T
    )

    suspend fun getAllSettings(): MainState
}