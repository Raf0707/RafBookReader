package raf.console.chitalka.data.parser

import raf.console.chitalka.domain.model.BookWithCover
import java.io.File


interface FileParser {

    suspend fun parse(file: File): BookWithCover?
}