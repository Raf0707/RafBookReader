package com.byteflipper.book_story.domain.use_case.data_store

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.byteflipper.book_story.domain.repository.DataStoreRepository
import com.byteflipper.book_story.presentation.core.constants.DataStoreConstants
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