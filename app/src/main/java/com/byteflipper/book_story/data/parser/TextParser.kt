package com.byteflipper.book_story.data.parser

import com.byteflipper.book_story.domain.model.ChapterWithText
import com.byteflipper.book_story.domain.util.Resource
import java.io.File

interface TextParser {
    suspend fun parse(file: File): Resource<List<ChapterWithText>>
}