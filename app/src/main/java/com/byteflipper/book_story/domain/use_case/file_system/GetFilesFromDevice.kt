package com.byteflipper.book_story.domain.use_case.file_system

import com.byteflipper.book_story.domain.model.SelectableFile
import com.byteflipper.book_story.domain.repository.FileSystemRepository
import javax.inject.Inject

class GetFilesFromDevice @Inject constructor(
    private val repository: FileSystemRepository
) {

    suspend fun execute(query: String): List<SelectableFile> {
        return repository.getFilesFromDevice(query)
    }
}