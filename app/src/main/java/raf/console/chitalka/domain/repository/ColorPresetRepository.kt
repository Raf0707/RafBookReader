/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.repository

import raf.console.chitalka.domain.reader.ColorPreset

interface ColorPresetRepository {

    suspend fun updateColorPreset(
        colorPreset: ColorPreset
    )

    suspend fun selectColorPreset(
        colorPreset: ColorPreset
    )

    suspend fun getColorPresets(): List<ColorPreset>

    suspend fun reorderColorPresets(
        orderedColorPresets: List<ColorPreset>
    )

    suspend fun deleteColorPreset(
        colorPreset: ColorPreset
    )
}