package com.byteflipper.book_story.domain.repository

interface FavoriteDirectoryRepository {

    suspend fun updateFavoriteDirectory(path: String)
}