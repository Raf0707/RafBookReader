/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.color_preset

import raf.console.chitalka.domain.reader.ColorPreset
import raf.console.chitalka.domain.repository.ColorPresetRepository
import javax.inject.Inject

class GetColorPresets @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(): List<ColorPreset> {
        return repository.getColorPresets()
    }
}