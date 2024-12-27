package raf.console.chitalka.domain.repository

interface FavoriteDirectoryRepository {

    suspend fun updateFavoriteDirectory(path: String)
}