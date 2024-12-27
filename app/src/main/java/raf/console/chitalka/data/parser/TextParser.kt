package raf.console.chitalka.data.parser

import raf.console.chitalka.domain.model.ChapterWithText
import raf.console.chitalka.domain.util.Resource
import java.io.File

interface TextParser {
    suspend fun parse(file: File): Resource<List<ChapterWithText>>
}