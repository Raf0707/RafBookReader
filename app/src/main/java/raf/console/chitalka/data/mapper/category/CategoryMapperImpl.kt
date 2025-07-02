/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.category

import raf.console.chitalka.data.local.dto.CategoryEntity
import raf.console.chitalka.domain.library.custom_category.Category
import javax.inject.Inject

class CategoryMapperImpl @Inject constructor() : CategoryMapper {
    override fun toEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            id = category.id,
            name = category.name,
            kind = category.kind,
            isVisible = category.isVisible,
            position = category.position,
            isDefault = category.isDefault
        )
    }

    override fun toDomain(entity: CategoryEntity): Category {
        val localizedName = if (entity.isDefault) {
            if (entity.id == 0) {
                raf.console.chitalka.domain.ui.UIText.StringResource(raf.console.chitalka.R.string.all_tab)
            } else
            when (entity.id) {
                1 -> raf.console.chitalka.domain.ui.UIText.StringResource(raf.console.chitalka.R.string.reading_tab)
                2 -> raf.console.chitalka.domain.ui.UIText.StringResource(raf.console.chitalka.R.string.already_read_tab)
                3 -> raf.console.chitalka.domain.ui.UIText.StringResource(raf.console.chitalka.R.string.planning_tab)
                4 -> raf.console.chitalka.domain.ui.UIText.StringResource(raf.console.chitalka.R.string.dropped_tab)
                else -> raf.console.chitalka.domain.ui.UIText.StringValue(entity.name)
            }
        } else raf.console.chitalka.domain.ui.UIText.StringValue(entity.name)
        return Category(
            id = entity.id,
            name = entity.name,
            kind = entity.kind,
            isVisible = entity.isVisible,
            position = entity.position,
            isDefault = entity.isDefault,
            title = localizedName
        )
    }
} 