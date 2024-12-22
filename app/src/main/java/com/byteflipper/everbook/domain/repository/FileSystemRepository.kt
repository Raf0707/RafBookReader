package com.byteflipper.everbook.domain.repository

import com.byteflipper.everbook.domain.model.NullableBook
import com.byteflipper.everbook.domain.model.SelectableFile
import java.io.File

interface FileSystemRepository {

    suspend fun getFilesFromDevice(
        query: String = ""
    ): List<SelectableFile>

    suspend fun getBookFromFile(
        file: File
    ): NullableBook
}