/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.parser.pdf

import android.app.Application
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.yield
import raf.console.chitalka.data.parser.MarkdownParser
import raf.console.chitalka.data.parser.TextParser
import raf.console.chitalka.domain.file.CachedFile
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.presentation.core.util.clearAllMarkdown
import java.io.File
import java.io.IOException
import javax.inject.Inject

private const val PDF_TAG = "PDF Parser"

class PdfTextParser @Inject constructor(
    private val markdownParser: MarkdownParser,
    private val application: Application
) : TextParser {

    override suspend fun parse(cachedFile: CachedFile): List<ReaderText> {
        Log.i(PDF_TAG, "Started PDF parsing: ${cachedFile.name}.")

        return try {
            yield()
            PDFBoxResourceLoader.init(application)
            yield()

            val readerText = mutableListOf<ReaderText>()
            val pdfStripper = PDFTextStripper().apply {
                paragraphStart = "</br>"
            }

            var extractedText = ""

            // ---- Попытка извлечения текста ----
            PDDocument.load(cachedFile.openInputStream()).use { doc ->
                extractedText = pdfStripper.getText(doc).replace("\r", "")
            }

            yield()

            // ---- Если текст не найден, переходим к изображённому PDF ----
            if (extractedText.isBlank()) {
                Log.w(PDF_TAG, "PDF has no text — trying to extract images as pages.")

                try {
                    val fd = application.contentResolver.openFileDescriptor(cachedFile.uri, "r")
                        ?: throw IOException("Cannot open file descriptor for ${cachedFile.uri}")

                    val pdfRenderer = PdfRenderer(fd)
                    for (i in 0 until pdfRenderer.pageCount) {
                        yield()
                        pdfRenderer.openPage(i).use { page ->
                            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                            readerText.add(
                                ReaderText.Image(imageBitmap = bitmap.asImageBitmap())
                            )
                        }
                    }

                    pdfRenderer.close()
                    fd.close()
                    Log.i(PDF_TAG, "Extracted ${readerText.size} image pages from PDF.")
                } catch (e: SecurityException) {
                    Log.e(PDF_TAG, "Permission denied while opening PDF URI.", e)
                } catch (e: Exception) {
                    Log.e(PDF_TAG, "Error rendering image-only PDF.", e)
                }

                return readerText
            }


            // ---- Обработка текстового PDF ----
            val text = extractedText.filterIndexed { index, c ->
                yield()
                if (c == ' ') extractedText.getOrNull(index - 1) != ' ' else true
            }

            yield()

            val unformattedLines = text.split("${pdfStripper.paragraphStart}|\\n".toRegex())
                .filter { it.isNotBlank() }

            yield()

            val lines = mutableListOf<String>()
            unformattedLines.forEachIndexed { index, string ->
                try {
                    yield()
                    val line = string.trim()
                    if (index == 0) {
                        lines.add(line)
                        return@forEachIndexed
                    }

                    if (line.all { it.isDigit() }) return@forEachIndexed

                    val firstChar = line.firstOrNull() ?: return@forEachIndexed

                    when {
                        firstChar.isLowerCase() -> {
                            val current = lines.last()
                            lines[lines.lastIndex] = if (current.endsWith("-")) {
                                current.dropLast(1) + line
                            } else {
                                "$current $line"
                            }
                        }
                        firstChar.isUpperCase() || firstChar.isDigit() -> lines.add(line)
                        firstChar.isLetter() -> {
                            lines[lines.lastIndex] += " $line"
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            yield()

            var chapterAdded = false
            lines.forEach { line ->
                yield()
                if (line.isNotBlank()) {
                    when (line) {
                        "***", "---" -> readerText.add(ReaderText.Separator)
                        else -> {
                            if (!chapterAdded && line.clearAllMarkdown().isNotBlank()) {
                                readerText.add(
                                    0, ReaderText.Chapter(
                                        title = line.clearAllMarkdown(),
                                        nested = false
                                    )
                                )
                                chapterAdded = true
                            } else {
                                readerText.add(
                                    ReaderText.Text(
                                        line = markdownParser.parse(line)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            yield()

            if (
                readerText.none { it is ReaderText.Text || it is ReaderText.Image || it is ReaderText.Chapter }
            ) {
                Log.e(PDF_TAG, "Could not extract any readable content from PDF.")
                return emptyList()
            }

            Log.i(PDF_TAG, "Successfully parsed PDF content (${readerText.size} items).")

            Log.i(PDF_TAG, "Successfully finished PDF parsing.")
            readerText

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
