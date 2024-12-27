package raf.console.chitalka.domain.use_case.file_system

import raf.console.chitalka.domain.model.SelectableFile
import raf.console.chitalka.domain.repository.FileSystemRepository
import javax.inject.Inject

class GetFilesFromDevice @Inject constructor(
    private val repository: FileSystemRepository
) {

    suspend fun execute(query: String): List<SelectableFile> {
        return repository.getFilesFromDevice(query)
    }
}