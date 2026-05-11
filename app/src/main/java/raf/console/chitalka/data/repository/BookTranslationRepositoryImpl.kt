/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.repository

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import raf.console.chitalka.domain.file.CachedFileCompat
import raf.console.chitalka.domain.repository.BookTranslationRepository
import raf.console.chitalka.domain.translation.BookTranslationCacheKey
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

private const val BOOK_TRANSLATION_CACHE = "BOOK_TRANSLATION_CACHE"

@Singleton
class BookTranslationRepositoryImpl @Inject constructor(
    private val application: Application,
    private val gson: Gson
) : BookTranslationRepository {

    private val cacheDir: File
        get() = File(application.filesDir, "book_translations")

    override suspend fun getFileFingerprint(filePath: String): String = withContext(Dispatchers.IO) {
        Log.i(BOOK_TRANSLATION_CACHE, "Resolving fingerprint for path=$filePath")
        val cachedFile = CachedFileCompat.fromFullPath(
            context = application,
            path = filePath,
            builder = CachedFileCompat.build(
                name = filePath.substringAfterLast(File.separator),
                path = filePath,
                isDirectory = false
            )
        )

        val size = cachedFile?.size ?: 0L
        val lastModified = cachedFile?.lastModified ?: 0L
        val fingerprint = "$size:$lastModified"
        Log.i(
            BOOK_TRANSLATION_CACHE,
            "Fingerprint resolved size=$size lastModified=$lastModified canAccess=${cachedFile?.canAccess()}"
        )
        fingerprint
    }

    override suspend fun readTranslation(
        key: BookTranslationCacheKey
    ): Map<Int, String>? = withContext(Dispatchers.IO) {
        val file = cacheFileFor(key)
        Log.i(BOOK_TRANSLATION_CACHE, "Read cache file=${file.absolutePath}")
        if (!file.exists()) {
            Log.i(BOOK_TRANSLATION_CACHE, "Cache file does not exist")
            return@withContext null
        }

        runCatching {
            val dto = gson.fromJson<TranslationCacheDto>(
                file.readText(),
                object : TypeToken<TranslationCacheDto>() {}.type
            )
            if (
                dto.bookId != key.bookId ||
                dto.filePathHash != key.filePath.sha256() ||
                dto.sourceLanguage != key.sourceLanguage ||
                dto.targetLanguage != key.targetLanguage ||
                dto.fileFingerprint != key.fileFingerprint
            ) {
                Log.w(
                    BOOK_TRANSLATION_CACHE,
                    "Cache metadata mismatch bookId=${dto.bookId}/${key.bookId} source=${dto.sourceLanguage}/${key.sourceLanguage} target=${dto.targetLanguage}/${key.targetLanguage} fingerprint=${dto.fileFingerprint}/${key.fileFingerprint}"
                )
                return@withContext null
            }

            Log.i(BOOK_TRANSLATION_CACHE, "Cache read success entries=${dto.entries.size}")
            dto.entries.associate { it.index to it.text }
        }.onFailure {
            Log.e(BOOK_TRANSLATION_CACHE, "Cache read failed", it)
        }.getOrNull()
    }

    override suspend fun writeTranslation(
        key: BookTranslationCacheKey,
        translatedTextByIndex: Map<Int, String>
    ) = withContext(Dispatchers.IO) {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        val dto = TranslationCacheDto(
            bookId = key.bookId,
            filePathHash = key.filePath.sha256(),
            sourceLanguage = key.sourceLanguage,
            targetLanguage = key.targetLanguage,
            fileFingerprint = key.fileFingerprint,
            entries = translatedTextByIndex.map { (index, text) ->
                TranslationCacheEntryDto(index = index, text = text)
            }
        )

        val file = cacheFileFor(key)
        Log.i(
            BOOK_TRANSLATION_CACHE,
            "Write cache file=${file.absolutePath} entries=${translatedTextByIndex.size}"
        )
        file.writeText(gson.toJson(dto))
    }

    private fun cacheFileFor(key: BookTranslationCacheKey): File {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        val fileName = listOf(
            key.bookId.toString(),
            key.filePath.sha256(),
            key.sourceLanguage,
            key.targetLanguage,
            key.fileFingerprint.sha256()
        ).joinToString(separator = "_")

        return File(cacheDir, "$fileName.json")
    }

    private fun String.sha256(): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(toByteArray())

        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }

    private data class TranslationCacheDto(
        val bookId: Int,
        val filePathHash: String,
        val sourceLanguage: String,
        val targetLanguage: String,
        val fileFingerprint: String,
        val entries: List<TranslationCacheEntryDto>
    )

    private data class TranslationCacheEntryDto(
        val index: Int,
        val text: String
    )
}
