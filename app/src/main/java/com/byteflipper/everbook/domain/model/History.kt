package com.byteflipper.everbook.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class History(
    val id: Int = 0,
    val bookId: Int,
    val book: Book?,
    val time: Long
)