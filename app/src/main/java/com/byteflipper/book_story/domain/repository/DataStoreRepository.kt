package com.byteflipper.book_story.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.byteflipper.book_story.presentation.data.MainState

interface DataStoreRepository {

    suspend fun <T> putDataToDataStore(
        key: Preferences.Key<T>,
        value: T
    )

    suspend fun getAllSettings(): MainState
}