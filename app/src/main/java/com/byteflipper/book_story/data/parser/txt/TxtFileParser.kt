package com.byteflipper.book_story.data.parser.txt

import com.byteflipper.book_story.R
import com.byteflipper.book_story.data.parser.FileParser
import com.byteflipper.book_story.domain.model.Book
import com.byteflipper.book_story.domain.model.BookWithCover
import com.byteflipper.book_story.domain.model.Category
import com.byteflipper.book_story.domain.util.UIText
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