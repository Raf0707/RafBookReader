/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.color_preset

import androidx.compose.ui.graphics.Color
import raf.console.chitalka.data.local.dto.ColorPresetEntity
import raf.console.chitalka.domain.reader.ColorPreset
import javax.inject.Inject

class ColorPresetMapperImpl @Inject constructor() : ColorPresetMapper {
    override suspend fun toColorPresetEntity(
        colorPreset: ColorPreset,
        order: Int
    ): ColorPresetEntity {
        return ColorPresetEntity(
            id = if (colorPreset.id != -1) colorPreset.id
            else null,
            name = colorPreset.name,
            backgroundColor = colorPreset.backgroundColor.value.toLong(),
            fontColor = colorPreset.fontColor.value.toLong(),
            isSelected = colorPreset.isSelected,
            order = order
        )
    }

    override suspend fun toColorPreset(colorPresetEntity: ColorPresetEntity): ColorPreset {
        return ColorPreset(
            id = colorPresetEntity.id!!,
            name = colorPresetEntity.name,
            backgroundColor = Color(colorPresetEntity.backgroundColor.toULong()),
            fontColor = Color(colorPresetEntity.fontColor.toULong()),
            isSelected = colorPresetEntity.isSelected
        )
    }
}