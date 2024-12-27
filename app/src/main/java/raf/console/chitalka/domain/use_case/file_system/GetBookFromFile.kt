package raf.console.chitalka.domain.use_case.file_system

import raf.console.chitalka.domain.model.NullableBook
import raf.console.chitalka.domain.repository.FileSystemRepository
import java.io.File
import javax.inject.Inject

class GetBookFromFile @Inject constructor(
    private val repository: FileSystemRepository
) {

    suspend fun execute(file: File): NullableBook {
        return repository.getBookFromFile(file)
    }
}