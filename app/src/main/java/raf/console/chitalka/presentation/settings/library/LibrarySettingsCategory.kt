/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.library

import androidx.compose.foundation.lazy.LazyListScope
import raf.console.chitalka.domain.library.custom_category.Category
import raf.console.chitalka.presentation.settings.library.subcategory.CategoriesSubcategory

/*fun LazyListScope.LibrarySettingsCategory(
    categories: List<Category>,
    onToggleVisibility: (Int, Boolean) -> Unit,
    onDelete: (Int, Int?) -> Unit,
    onRequestCreate: () -> Unit,
    onRequestRename: (Int, String) -> Unit
) {
    CategoriesSubcategory(
        categories = categories,
        onToggleVisibility = onToggleVisibility,
        onRequestRename = onRequestRename,
        onDelete = onDelete,
        onRequestCreate = onRequestCreate,
    )
}*/

fun LazyListScope.LibrarySettingsCategory(
    categories: List<Category>,
    onToggleVisibility: (Int, Boolean) -> Unit,
    onDelete: (Int, Int?) -> Unit,
    onRequestCreate: () -> Unit,
    onRequestRename: (Int, String) -> Unit,
    isEditMode: Boolean // 👈 добавлен параметр
) {
    CategoriesSubcategory(
        categories = categories,
        onToggleVisibility = onToggleVisibility,
        onRequestRename = onRequestRename,
        onDelete = onDelete,
        onRequestCreate = onRequestCreate,
        isEditMode = isEditMode // 👈 передаётся дальше
    )
}
