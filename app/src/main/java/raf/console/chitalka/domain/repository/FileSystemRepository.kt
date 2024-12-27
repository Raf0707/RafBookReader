package raf.console.chitalka.domain.repository

import raf.console.chitalka.domain.model.NullableBook
import raf.console.chitalka.domain.model.SelectableFile
import java.io.File

interface FileSystemRepository {

    suspend fun getFilesFromDevice(
        query: String = ""
    ): List<SelectableFile>

    suspend fun getBookFromFile(
        file: File
    ): NullableBook
}