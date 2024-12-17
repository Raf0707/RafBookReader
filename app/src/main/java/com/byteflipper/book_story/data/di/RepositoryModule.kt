package com.byteflipper.book_story.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.byteflipper.book_story.data.local.data_store.DataStore
import com.byteflipper.book_story.data.local.data_store.DataStoreImpl
import com.byteflipper.book_story.data.local.notification.UpdatesNotificationService
import com.byteflipper.book_story.data.local.notification.UpdatesNotificationServiceImpl
import com.byteflipper.book_story.data.mapper.book.BookMapper
import com.byteflipper.book_story.data.mapper.book.BookMapperImpl
import com.byteflipper.book_story.data.mapper.color_preset.ColorPresetMapper
import com.byteflipper.book_story.data.mapper.color_preset.ColorPresetMapperImpl
import com.byteflipper.book_story.data.mapper.history.HistoryMapper
import com.byteflipper.book_story.data.mapper.history.HistoryMapperImpl
import com.byteflipper.book_story.data.parser.FileParser
import com.byteflipper.book_story.data.parser.FileParserImpl
import com.byteflipper.book_story.data.parser.TextParser
import com.byteflipper.book_story.data.parser.TextParserImpl
import com.byteflipper.book_story.data.repository.BookRepositoryImpl
import com.byteflipper.book_story.data.repository.ColorPresetRepositoryImpl
import com.byteflipper.book_story.data.repository.DataStoreRepositoryImpl
import com.byteflipper.book_story.data.repository.FavoriteDirectoryRepositoryImpl
import com.byteflipper.book_story.data.repository.FileSystemRepositoryImpl
import com.byteflipper.book_story.data.repository.HistoryRepositoryImpl
import com.byteflipper.book_story.data.repository.RemoteRepositoryImpl
import com.byteflipper.book_story.domain.repository.BookRepository
import com.byteflipper.book_story.domain.repository.ColorPresetRepository
import com.byteflipper.book_story.domain.repository.DataStoreRepository
import com.byteflipper.book_story.domain.repository.FavoriteDirectoryRepository
import com.byteflipper.book_story.domain.repository.FileSystemRepository
import com.byteflipper.book_story.domain.repository.HistoryRepository
import com.byteflipper.book_story.domain.repository.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDataStore(
        dataStoreImpl: DataStoreImpl
    ): DataStore

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository

    @Binds
    @Singleton
    abstract fun bindColorPresetRepository(
        colorPresetRepositoryImpl: ColorPresetRepositoryImpl
    ): ColorPresetRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindFileSystemRepository(
        fileSystemRepositoryImpl: FileSystemRepositoryImpl
    ): FileSystemRepository

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteDirectoryRepository(
        favoriteDirectoryRepositoryImpl: FavoriteDirectoryRepositoryImpl
    ): FavoriteDirectoryRepository

    @Binds
    @Singleton
    abstract fun bindBookMapper(
        bookMapperImpl: BookMapperImpl
    ): BookMapper

    @Binds
    @Singleton
    abstract fun bindHistoryMapper(
        historyMapperImpl: HistoryMapperImpl
    ): HistoryMapper

    @Binds
    @Singleton
    abstract fun bindColorPresetMapper(
        colorPresetMapperImpl: ColorPresetMapperImpl
    ): ColorPresetMapper

    @Binds
    @Singleton
    abstract fun bindFileParser(
        fileParserImpl: FileParserImpl
    ): FileParser

    @Binds
    @Singleton
    abstract fun bindTextParser(
        textParserImpl: TextParserImpl
    ): TextParser

    @Binds
    @Singleton
    abstract fun bindNotificationService(
        updatesNotificationServiceImpl: UpdatesNotificationServiceImpl
    ): UpdatesNotificationService
}