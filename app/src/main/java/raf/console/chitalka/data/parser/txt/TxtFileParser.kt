package raf.console.chitalka.data.parser.txt

import raf.console.chitalka.R
import raf.console.chitalka.data.parser.FileParser
import raf.console.chitalka.domain.model.Book
import raf.console.chitalka.domain.model.BookWithCover
import raf.console.chitalka.domain.model.Category
import raf.console.chitalka.domain.util.UIText
import java.io.File
import javax.inject.Inject

class TxtFileParser @Inject constructor() : FileParser {

    override suspend fun parse(file: File): BookWithCover? {
        return try {
            val title = file.nameWithoutExtension.trim()
            val author = UIText.StringResource(R.string.unknown_author)

            BookWithCover(
                book = Book(
                    title = title,
                    author = author,
                    description = null,
                    textPath = "",
                    scrollIndex = 0,
                    scrollOffset = 0,
                    progress = 0f,
                    filePath = file.path,
                    lastOpened = null,
                    category = Category.entries[0],
                    coverImage = null
                ),
                coverImage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}