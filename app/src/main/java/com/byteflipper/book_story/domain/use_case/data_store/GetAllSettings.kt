package com.byteflipper.book_story.domain.use_case.data_store

import com.byteflipper.book_story.domain.repository.DataStoreRepository
import com.byteflipper.book_story.presentation.data.MainState
import javax.inject.Inject

class GetAllSettings @Inject constructor(
    private val repository: DataStoreRepository
) {

    suspend fun execute(): MainState {
        return repository.getAllSettings()
    }
}