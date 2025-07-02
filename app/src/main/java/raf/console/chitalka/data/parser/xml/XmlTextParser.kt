/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.parser.xml

import android.util.Log
import kotlinx.coroutines.yield
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import raf.console.chitalka.data.parser.DocumentParser
import raf.console.chitalka.data.parser.TextParser
import raf.console.chitalka.domain.file.CachedFile
import raf.console.chitalka.domain.reader.ReaderText
import javax.inject.Inject

private const val XML_TAG = "XML Parser"

class XmlTextParser @Inject constructor(
    private val documentParser: DocumentParser
) : TextParser {

    override suspend fun parse(cachedFile: CachedFile): List<ReaderText> {
        Log.i(XML_TAG, "Started XML parsing: ${cachedFile.name}.")

        return try {
            val readerText = cachedFile.openInputStream()?.use { stream ->
                documentParser.parseDocument(Jsoup.parse(stream, null, "", Parser.xmlParser()))
            }

            yield()

            if (
                readerText.isNullOrEmpty() ||
                readerText.filterIsInstance<ReaderText.Text>().isEmpty() ||
                readerText.filterIsInstance<ReaderText.Chapter>().isEmpty()
            ) {
                Log.e(XML_TAG, "Could not extract text from XML.")
                return emptyList()
            }

            Log.i(XML_TAG, "Successfully finished XML parsing.")
            readerText
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}