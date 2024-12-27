package raf.console.chitalka.data.parser

import android.util.Log
import raf.console.chitalka.data.parser.epub.EpubFileParser
import raf.console.chitalka.data.parser.fb2.Fb2FileParser
import raf.console.chitalka.data.parser.html.HtmlFileParser
import raf.console.chitalka.data.parser.pdf.PdfFileParser
import raf.console.chitalka.data.parser.txt.TxtFileParser
import raf.console.chitalka.domain.model.BookWithCover
import java.io.File
import javax.inject.Inject

private const val FILE_PARSER = "File Parser"

class FileParserImpl @Inject constructor(
    private val txtFileParser: TxtFileParser,
    private val pdfFileParser: PdfFileParser,
    private val epubFileParser: EpubFileParser,
    private val fb2FileParser: Fb2FileParser,
    private val htmlFileParser: HtmlFileParser,
) : FileParser {
    override suspend fun parse(file: File): BookWithCover? {
        if (!file.exists()) {
            Log.e(FILE_PARSER, "File does not exist.")
            return null
        }

        val fileFormat = ".${file.extension}".lowercase().trim()
        return when (fileFormat) {
            ".pdf" -> {
                pdfFileParser.parse(file)
            }

            ".epub" -> {
                epubFileParser.parse(file)
            }

            ".txt" -> {
                txtFileParser.parse(file)
            }

            ".fb2" -> {
                fb2FileParser.parse(file)
            }

            ".zip" -> {
                epubFileParser.parse(file)
            }

            ".html" -> {
                htmlFileParser.parse(file)
            }

            ".htm" -> {
                htmlFileParser.parse(file)
            }

            ".md" -> {
                txtFileParser.parse(file)
            }

            else -> {
                Log.e(FILE_PARSER, "Wrong file format, could not find supported extension.")
                null
            }
        }
    }
}








