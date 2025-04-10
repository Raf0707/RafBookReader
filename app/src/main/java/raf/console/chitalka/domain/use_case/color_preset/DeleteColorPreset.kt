/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.color_preset

import raf.console.chitalka.domain.reader.ColorPreset
import raf.console.chitalka.domain.repository.ColorPresetRepository
import javax.inject.Inject

class DeleteColorPreset @Inject constructor(
    private val repository: ColorPresetRepository
) {

    suspend fun execute(colorPreset: ColorPreset) {
        repository.deleteColorPreset(colorPreset)
    }
}