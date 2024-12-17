package com.byteflipper.book_story.domain.repository

import com.byteflipper.book_story.domain.model.NullableBook
import com.byteflipper.book_story.domain.model.SelectableFile
import java.io.File

interface FileSystemRepository {

    suspend fun getFilesFromDevice(
        query: String = ""
    ): List<SelectableFile>

    suspend fun getBookFromFile(
        file: File
    ): NullableBook
}