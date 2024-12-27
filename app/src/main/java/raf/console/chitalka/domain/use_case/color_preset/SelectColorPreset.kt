package raf.console.chitalka.domain.use_case.color_preset

import raf.console.chitalka.domain.model.ColorPreset
import raf.console.chitalka.domain.repository.ColorPresetRepository
import javax.inject.Inject

class SelectColorPreset @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(colorPreset: ColorPreset) {
        repository.selectColorPreset(colorPreset)
    }
}