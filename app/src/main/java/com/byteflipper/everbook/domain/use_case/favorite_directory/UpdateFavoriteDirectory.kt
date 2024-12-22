package com.byteflipper.everbook.domain.use_case.favorite_directory

import com.byteflipper.everbook.domain.repository.FavoriteDirectoryRepository
import javax.inject.Inject

class UpdateFavoriteDirectory @Inject constructor(
    private val repository: FavoriteDirectoryRepository
) {

    suspend fun execute(path: String) {
        return repository.updateFavoriteDirectory(path)
    }
}