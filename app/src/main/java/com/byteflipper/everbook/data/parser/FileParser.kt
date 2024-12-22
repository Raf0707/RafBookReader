package com.byteflipper.everbook.data.parser

import com.byteflipper.everbook.domain.model.BookWithCover
import java.io.File


interface FileParser {

    suspend fun parse(file: File): BookWithCover?
}