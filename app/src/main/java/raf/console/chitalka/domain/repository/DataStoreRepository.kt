package raf.console.chitalka.domain.repository

import androidx.datastore.preferences.core.Preferences
import raf.console.chitalka.presentation.data.MainState

interface DataStoreRepository {

    suspend fun <T> putDataToDataStore(
        key: Preferences.Key<T>,
        value: T
    )

    suspend fun getAllSettings(): MainState
}