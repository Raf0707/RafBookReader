/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import raf.console.chitalka.data.local.data_store.DataStore
import raf.console.chitalka.data.local.data_store.DataStoreImpl
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
import raf.console.chitalka.data.repository.FileSystemRepositoryImpl
import raf.console.chitalka.data.repository.HistoryRepositoryImpl
import raf.console.chitalka.data.repository.PermissionRepositoryImpl
import raf.console.chitalka.domain.repository.BookRepository
import raf.console.chitalka.domain.repository.ColorPresetRepository
import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.domain.repository.FileSystemRepository
import raf.console.chitalka.domain.repository.HistoryRepository
import raf.console.chitalka.domain.repository.PermissionRepository
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
    abstract fun bindPermissionRepository(
        permissionRepositoryImpl: PermissionRepositoryImpl
    ): PermissionRepository

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
}