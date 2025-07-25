/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.repository

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import raf.console.chitalka.data.local.data_store.DataStore
import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.ui.main.MainState
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private const val GET_ALL_SETTINGS = "GET SETTINGS, REPO"

/**
 * Data Store repository.
 * Manages all [DataStore] related work.
 */
@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore
) : DataStoreRepository {

    /**
     * Puts DataStore constant to [DataStore].
     */
    override suspend fun <T> putDataToDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.putData(key, value)
    }

    /**
     * Gets all settings from DataStore and returns [MainState].
     */
    override suspend fun getAllSettings(): MainState {
        Log.i(GET_ALL_SETTINGS, "Getting all settings.")
        val result = CompletableDeferred<MainState>()

        withContext(Dispatchers.Default) {
            val keys = dataStore.getAllData()
            val data = ConcurrentHashMap<String, Any>()

            Log.i(GET_ALL_SETTINGS, "Got ${keys?.size} settings keys.")

            val jobs = keys?.map { key ->
                async {
                    val nullableData = dataStore.getNullableData(key)

                    if (nullableData == null) {
                        data.remove(key.name)
                    } else {
                        data[key.name] = nullableData
                    }
                }
            }
            jobs?.awaitAll()

            result.complete(MainState.initialize(data))
        }

        return result.await()
    }
}