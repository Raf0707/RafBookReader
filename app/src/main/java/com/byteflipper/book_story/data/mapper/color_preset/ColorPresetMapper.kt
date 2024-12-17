package com.byteflipper.book_story.data.mapper.color_preset

import com.byteflipper.book_story.data.local.dto.ColorPresetEntity
import com.byteflipper.book_story.domain.model.ColorPreset

interface ColorPresetMapper {
    suspend fun toColorPresetEntity(colorPreset: ColorPreset, order: Int): ColorPresetEntity

    suspend fun toColorPreset(colorPresetEntity: ColorPresetEntity): ColorPreset
}