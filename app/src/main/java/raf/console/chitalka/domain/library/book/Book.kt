/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.library.book

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.domain.library.category.Category
import raf.console.chitalka.domain.ui.UIText

@Parcelize
@Immutable
data class Book(
    val id: Int = 0,

    val title: String,
    val author: UIText,
    val description: String?,

    val filePath: String,
    val coverImage: Uri?,

    val scrollIndex: Int,
    val scrollOffset: Int,
    val progress: Float,

    val lastOpened: Long?,
    val categoryId: Int = 0,
    @Deprecated("Use categoryId")
    val category: Category? = null,

    /**
     * Список всех категорий, к которым принадлежит книга (включая 0 – «Все»).
     * Поле появилось при переходе на модель many-to-many. Для обратной совместимости
     * отдельное поле [categoryId] всё ещё хранит «основную» категорию.
     */
    val categoryIds: List<Int> = emptyList()
) : Parcelable