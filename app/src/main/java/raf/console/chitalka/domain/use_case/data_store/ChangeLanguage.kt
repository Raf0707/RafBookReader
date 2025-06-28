/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.data_store

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import raf.console.chitalka.domain.repository.DataStoreRepository
import raf.console.chitalka.presentation.core.constants.DataStoreConstants
import javax.inject.Inject

class ChangeLanguage @Inject constructor(
    private val repository: DataStoreRepository
) {

    suspend fun execute(language: String) {
        val appLocale = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)

        repository.putDataToDataStore(
            DataStoreConstants.LANGUAGE,
            language
        )
    }
}