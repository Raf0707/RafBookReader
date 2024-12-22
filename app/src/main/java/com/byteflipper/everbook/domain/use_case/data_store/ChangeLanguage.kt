package com.byteflipper.everbook.domain.use_case.data_store

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.byteflipper.everbook.domain.repository.DataStoreRepository
import com.byteflipper.everbook.presentation.core.constants.DataStoreConstants
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