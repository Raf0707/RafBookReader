/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.history

import raf.console.chitalka.data.local.dto.HistoryEntity
import raf.console.chitalka.domain.history.History

interface HistoryMapper {
    suspend fun toHistoryEntity(history: History): HistoryEntity

    suspend fun toHistory(historyEntity: HistoryEntity): History
}