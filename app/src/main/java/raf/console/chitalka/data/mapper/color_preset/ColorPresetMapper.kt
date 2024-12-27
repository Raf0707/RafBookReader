package raf.console.chitalka.data.mapper.color_preset

import raf.console.chitalka.data.local.dto.ColorPresetEntity
import raf.console.chitalka.domain.model.ColorPreset

interface ColorPresetMapper {
    suspend fun toColorPresetEntity(colorPreset: ColorPreset, order: Int): ColorPresetEntity

    suspend fun toColorPreset(colorPresetEntity: ColorPresetEntity): ColorPreset
}