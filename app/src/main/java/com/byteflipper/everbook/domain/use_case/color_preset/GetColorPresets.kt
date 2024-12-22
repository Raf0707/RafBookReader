package com.byteflipper.everbook.domain.use_case.color_preset

import com.byteflipper.everbook.domain.model.ColorPreset
import com.byteflipper.everbook.domain.repository.ColorPresetRepository
import javax.inject.Inject

class GetColorPresets @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(): List<ColorPreset> {
        return repository.getColorPresets()
    }
}