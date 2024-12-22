package com.byteflipper.everbook.domain.repository

interface FavoriteDirectoryRepository {

    suspend fun updateFavoriteDirectory(path: String)
}