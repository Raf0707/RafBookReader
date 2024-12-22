package com.byteflipper.everbook.data.parser

import com.byteflipper.everbook.domain.model.ChapterWithText
import com.byteflipper.everbook.domain.util.Resource
import java.io.File

interface TextParser {
    suspend fun parse(file: File): Resource<List<ChapterWithText>>
}