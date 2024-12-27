package raf.console.chitalka.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import raf.console.chitalka.data.local.data_store.DataStore
import raf.console.chitalka.data.local.data_store.DataStoreImpl
import raf.console.chitalka.data.local.notification.UpdatesNotificationService
import raf.console.chitalka.data.local.notification.UpdatesNotificationServiceImpl
import raf.console.chitalka.data.mapper.book.BookMapper
import raf.console.chitalka.data.mapper.book.BookMapperImpl
import raf.console.chitalka.data.mapper.color_preset.ColorPresetMapper
import raf.console.chitalka.data.mapper.color_preset.ColorPresetMapperImpl
import raf.console.chitalka.data.mapper.history.HistoryMapper
import raf.console.chitalka.data.mapper.history.HistoryMapperImpl
import raf.console.chitalka.data.parser.FileParser
import raf.console.chitalka.data.parser.FileParserImpl
import raf.console.chitalka.data.parser.TextParser
import raf.console.chitalka.data.parser.TextParserImpl
import raf.console.chitalka.data.repository.BookRepositoryImpl
import raf.console.chitalka.data.repository.ColorPresetRepositoryImpl
import raf.console.chitalka.data.repository.DataStoreRepositoryImpl
import raf.console.chitalka.data.repository.FavoriteDirectoryRepositoryImpl
import raf.console.chitalka.data.repository.FileSystemRepositoryImpl
import raf.console.chitalka.data.repository.HistoryRepositoryImpl
import raf.console.chitalka.data.repository.RemoteRepositoryImpl
import raf.console.chitalka.domain.repository.BookRepository
import raf.console.chitalka.domain.repository.ColorPresetRepository
import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.domain.repository.FavoriteDirectoryRepository
import raf.console.chitalka.domain.repository.FileSystemRepository
import raf.console.chitalka.domain.repository.HistoryRepository
import raf.console.chitalka.domain.repository.RemoteRepository
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