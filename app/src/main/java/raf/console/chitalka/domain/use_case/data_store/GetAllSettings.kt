package raf.console.chitalka.domain.use_case.data_store

import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.presentation.data.MainState
import javax.inject.Inject

class GetAllSettings @Inject constructor(
    private val repository: DataStoreRepository
) {

    suspend fun execute(): MainState {
        return repository.getAllSettings()
    }
}