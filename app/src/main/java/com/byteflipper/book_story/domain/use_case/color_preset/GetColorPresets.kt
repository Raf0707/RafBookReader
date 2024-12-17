package com.byteflipper.book_story.domain.use_case.color_preset

import com.byteflipper.book_story.domain.model.ColorPreset
import com.byteflipper.book_story.domain.repository.ColorPresetRepository
import javax.inject.Inject

class GetColorPresets @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(): List<ColorPreset> {
        return repository.getColorPresets()
    }
}