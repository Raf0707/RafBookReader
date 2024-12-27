package raf.console.chitalka.domain.use_case.color_preset

import raf.console.chitalka.domain.model.ColorPreset
import raf.console.chitalka.domain.repository.ColorPresetRepository
import javax.inject.Inject

class GetColorPresets @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(): List<ColorPreset> {
        return repository.getColorPresets()
    }
}