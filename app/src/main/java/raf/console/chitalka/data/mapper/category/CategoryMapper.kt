/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.category

import raf.console.chitalka.data.local.dto.CategoryEntity
import raf.console.chitalka.domain.library.custom_category.Category

interface CategoryMapper {
    fun toEntity(category: Category): CategoryEntity

    fun toDomain(entity: CategoryEntity): Category
} 