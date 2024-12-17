package com.byteflipper.book_story.domain.use_case.color_preset

import com.byteflipper.book_story.domain.model.ColorPreset
import com.byteflipper.book_story.domain.repository.ColorPresetRepository
import javax.inject.Inject

class SelectColorPreset @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(colorPreset: ColorPreset) {
        repository.selectColorPreset(colorPreset)
    }
}