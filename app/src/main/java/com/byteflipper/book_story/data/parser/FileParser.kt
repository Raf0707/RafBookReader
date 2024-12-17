package com.byteflipper.book_story.data.parser

import com.byteflipper.book_story.domain.model.BookWithCover
import java.io.File


interface FileParser {

    suspend fun parse(file: File): BookWithCover?
}