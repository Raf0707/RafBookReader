/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.translation

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import com.google.android.gms.tasks.Task
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.repository.BookTranslationRepository
import raf.console.chitalka.domain.translation.BookTranslationCacheKey
import raf.console.chitalka.domain.translation.BookTranslationLanguage
import raf.console.chitalka.domain.translation.BookTranslationProgress
import raf.console.chitalka.domain.translation.BookTranslationStatus
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TEXT_TRANSLATION_TIMEOUT_MS = 60_000L
private const val BOOK_TRANSLATION = "BOOK_TRANSLATION"

class TranslateBook @Inject constructor(
    private val application: Application,
    private val repository: BookTranslationRepository
) {

    suspend fun execute(
        bookId: Int,
        filePath: String,
        text: List<ReaderText>,
        sourceLanguage: BookTranslationLanguage,
        targetLanguage: BookTranslationLanguage,
        startIndex: Int,
        onPartialTranslated: (index: Int, item: ReaderText) -> Unit,
        onCacheHit: () -> Unit,
        onProgress: (BookTranslationProgress) -> Unit
    ): List<ReaderText> = withContext(Dispatchers.IO) {
        Log.i(
            BOOK_TRANSLATION,
            "Start translation bookId=$bookId source=${sourceLanguage.mlKitTag} target=${targetLanguage.mlKitTag} items=${text.size}"
        )

        val fingerprint = repository.getFileFingerprint(filePath)
        Log.i(BOOK_TRANSLATION, "File fingerprint=$fingerprint path=$filePath")

        val cacheKey = BookTranslationCacheKey(
            bookId = bookId,
            filePath = filePath,
            sourceLanguage = sourceLanguage.mlKitTag,
            targetLanguage = targetLanguage.mlKitTag,
            fileFingerprint = fingerprint
        )

        repository.readTranslation(cacheKey)?.let { cached ->
            Log.i(BOOK_TRANSLATION, "Cache hit entries=${cached.size}")
            onCacheHit()
            val translated = applyTranslations(text, cached)
            translated.forEachIndexed { index, item ->
                if (cached.containsKey(index)) {
                    onPartialTranslated(index, item)
                }
            }
            return@withContext translated
        }
        Log.i(BOOK_TRANSLATION, "Cache miss")

        val translator = Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage.mlKitTag)
                .setTargetLanguage(targetLanguage.mlKitTag)
                .build()
        )

        try {
            onProgress(
                BookTranslationProgress(
                    status = BookTranslationStatus.CheckingModels,
                    progress = 0f,
                    message = "Проверка языковых моделей"
                )
            )
            val downloadedLanguages = getDownloadedTranslationLanguages()
            logDownloadedModels(sourceLanguage, targetLanguage, downloadedLanguages)
            val needsModelDownload = sourceLanguage.mlKitTag !in downloadedLanguages ||
                    targetLanguage.mlKitTag !in downloadedLanguages

            if (needsModelDownload && !application.hasInternetConnection()) {
                Log.e(BOOK_TRANSLATION, "No active internet connection before model download")
                throw IllegalStateException("Нет подключения к интернету для скачивания языковой модели.")
            }

            onProgress(
                BookTranslationProgress(
                    status = BookTranslationStatus.DownloadingModel,
                    progress = 0f,
                    message = if (needsModelDownload) {
                        "Скачивание языковой модели. Это может занять несколько минут"
                    } else {
                        "Подготовка языковой модели"
                    }
                )
            )
            Log.i(
                BOOK_TRANSLATION,
                "Preparing ML Kit model source=${sourceLanguage.mlKitTag} target=${targetLanguage.mlKitTag} needsDownload=$needsModelDownload"
            )
            val conditions = DownloadConditions.Builder().build()
            try {
                translator.downloadModelIfNeeded(conditions).await()
            } catch (e: Exception) {
                Log.e(BOOK_TRANSLATION, "ML Kit model download failed", e)
                throw IllegalStateException(
                    "Не удалось загрузить языковую модель. Проверьте интернет, свободное место и сервисы Google Play.",
                    e
                )
            }
            Log.i(BOOK_TRANSLATION, "ML Kit model is ready")
            coroutineContext.ensureActive()

            val translatableIndexes = text.withIndex()
                .filter { (_, item) -> item is ReaderText.Text || item is ReaderText.Chapter }
                .map { it.index }
                .prioritizeFrom(startIndex.coerceIn(0, text.lastIndex.coerceAtLeast(0)))
            val forwardTranslationCount = translatableIndexes.count { it >= startIndex }

            Log.i(BOOK_TRANSLATION, "Translatable items=${translatableIndexes.size}")

            if (translatableIndexes.isEmpty()) {
                Log.w(BOOK_TRANSLATION, "No translatable ReaderText items found")
                return@withContext text
            }

            val translations = linkedMapOf<Int, String>()

            translatableIndexes.forEachIndexed { progressIndex, textIndex ->
                coroutineContext.ensureActive()
                val item = text[textIndex]
                val source = when (item) {
                    is ReaderText.Text -> item.line.text
                    is ReaderText.Chapter -> item.title
                    else -> ""
                }

                val translated = if (source.isBlank()) {
                    source
                } else {
                    try {
                        withTimeout(TEXT_TRANSLATION_TIMEOUT_MS) {
                            translator.translate(source).await()
                        }
                    } catch (e: TimeoutCancellationException) {
                        Log.e(BOOK_TRANSLATION, "Text item translation timed out index=$textIndex", e)
                        throw IllegalStateException(
                            "Перевод занял слишком много времени. Попробуйте ещё раз.",
                            e
                        )
                    }
                }

                translations[textIndex] = translated
                val translatedItem = item.withTranslatedText(translated)
                onPartialTranslated(textIndex, translatedItem)
                Log.i(
                    BOOK_TRANSLATION,
                    "Translated item ${progressIndex + 1}/${translatableIndexes.size} index=$textIndex sourceLength=${source.length} targetLength=${translated.length}"
                )
                onProgress(
                    BookTranslationProgress(
                        status = BookTranslationStatus.Translating,
                        progress = (progressIndex + 1) / translatableIndexes.size.toFloat(),
                        message = if (progressIndex < forwardTranslationCount) {
                            "Перевод с текущей позиции"
                        } else {
                            "Перевод начала книги"
                        }
                    )
                )
            }

            repository.writeTranslation(cacheKey, translations)
            Log.i(BOOK_TRANSLATION, "Translation finished and cached entries=${translations.size}")
            applyTranslations(text, translations)
        } catch (e: CancellationException) {
            Log.w(BOOK_TRANSLATION, "Translation cancelled", e)
            throw e
        } catch (e: Exception) {
            Log.e(BOOK_TRANSLATION, "Translation failed", e)
            throw e
        } finally {
            Log.i(BOOK_TRANSLATION, "Closing ML Kit translator")
            translator.close()
        }
    }

    private fun applyTranslations(
        original: List<ReaderText>,
        translatedTextByIndex: Map<Int, String>
    ): List<ReaderText> {
        return original.mapIndexed { index, item ->
            val translated = translatedTextByIndex[index] ?: return@mapIndexed item
            item.withTranslatedText(translated)
        }
    }

    private fun ReaderText.withTranslatedText(translated: String): ReaderText {
        return when (this) {
            is ReaderText.Chapter -> copy(title = translated)
            is ReaderText.Text -> copy(line = line.withTranslatedText(translated))
            else -> this
        }
    }

    private fun List<Int>.prioritizeFrom(startIndex: Int): List<Int> {
        if (isEmpty()) return this
        val forward = filter { it >= startIndex }
        val backward = filter { it < startIndex }
        return forward + backward
    }

    private fun AnnotatedString.withTranslatedText(translated: String): AnnotatedString {
        val originalLength = text.length
        val translatedLength = translated.length
        val sameLength = originalLength == translatedLength

        val safeSpanStyles = spanStyles.mapNotNull { range ->
            when {
                sameLength -> range
                range.start == 0 && range.end == originalLength -> range.copy(end = translatedLength)
                else -> null
            }
        }

        val safeParagraphStyles = paragraphStyles.mapNotNull { range ->
            when {
                sameLength -> range
                range.start == 0 && range.end == originalLength -> range.copy(end = translatedLength)
                else -> null
            }
        }

        return AnnotatedString(
            text = translated,
            spanStyles = safeSpanStyles,
            paragraphStyles = safeParagraphStyles
        )
    }

    private suspend fun getDownloadedTranslationLanguages(): Set<String> {
        return runCatching {
            RemoteModelManager.getInstance()
                .getDownloadedModels(TranslateRemoteModel::class.java)
                .await()
                .map { it.language }
                .toSet()
        }.onFailure {
            Log.w(BOOK_TRANSLATION, "Could not read downloaded ML Kit models", it)
        }.getOrDefault(emptySet())
    }

    private fun logDownloadedModels(
        sourceLanguage: BookTranslationLanguage,
        targetLanguage: BookTranslationLanguage,
        downloadedLanguages: Set<String>
    ) {
        runCatching {
            Log.i(
                BOOK_TRANSLATION,
                "Downloaded translation models count=${downloadedLanguages.size} models=${downloadedLanguages.joinToString()}"
            )
            Log.i(
                BOOK_TRANSLATION,
                "Requested sourceModelDownloaded=${sourceLanguage.mlKitTag in downloadedLanguages} targetModelDownloaded=${targetLanguage.mlKitTag in downloadedLanguages}"
            )
        }.onFailure {
            Log.w(BOOK_TRANSLATION, "Could not read downloaded ML Kit models", it)
        }
    }

    private suspend fun <T> Task<T>.await(): T {
        return suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                if (continuation.isActive) {
                    continuation.resume(result)
                }
            }
            addOnFailureListener { exception ->
                if (continuation.isActive) {
                    continuation.resumeWithException(exception)
                }
            }
            addOnCanceledListener {
                if (continuation.isActive) {
                    continuation.cancel()
                }
            }
        }
    }

    private fun Application.hasInternetConnection(): Boolean {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
            ?: return false
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
