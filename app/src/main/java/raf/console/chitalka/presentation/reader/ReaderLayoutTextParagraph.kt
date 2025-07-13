/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.reader.FontWithName
import raf.console.chitalka.domain.reader.ReaderFontThickness
import raf.console.chitalka.domain.reader.ReaderText.Text
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.util.noRippleClickable
import raf.console.chitalka.ui.reader.ReaderEvent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.text.style.TextAlign

private val INLINE_REGEX = Regex("\\$([^$]+)\\$")

@Composable
fun LazyItemScope.ReaderLayoutTextParagraph(
    paragraph: Text,
    activity: ComponentActivity,
    showMenu: Boolean,
    fontFamily: FontWithName,
    fontColor: Color,
    lineHeight: TextUnit,
    fontThickness: ReaderFontThickness,
    fontStyle: FontStyle,
    textAlignment: ReaderTextAlignment,
    horizontalAlignment: Alignment.Horizontal,
    fontSize: TextUnit,
    letterSpacing: TextUnit,
    sidePadding: Dp,
    paragraphIndentation: TextUnit,
    fullscreenMode: Boolean,
    doubleClickTranslation: Boolean,
    highlightedReading: Boolean,
    highlightedReadingThickness: FontWeight,
    toolbarHidden: Boolean,
    openTranslator: (ReaderEvent.OnOpenTranslator) -> Unit,
    menuVisibility: (ReaderEvent.OnMenuVisibility) -> Unit,
    highlightedText: String? // 👈 Добавляем сюда параметр для подсвечиваемого текста
) {
    val rawText = paragraph.line.text
    val matches = INLINE_REGEX.findAll(rawText).toList()

    // Если формул нет — используем обычный вывод
    if (matches.isEmpty() || !raf.console.chitalka.math.MathConfig.enabled) {
        Column(
            modifier = Modifier
                .animateItem(fadeInSpec = null, fadeOutSpec = null)
                .fillMaxWidth()
                .padding(horizontal = sidePadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = horizontalAlignment
        ) {
            val annotatedString = buildAnnotatedString {
                append(rawText)
                highlightedText?.let { highlight ->
                    val start = rawText.indexOf(highlight)
                    if (start >= 0) {
                        addStyle(
                            SpanStyle(background = Color.Yellow),
                            start,
                            start + highlight.length
                        )
                    }
                }
            }

            SelectableParagraph(
                text = annotatedString.text,
                style = TextStyle(
                    fontFamily = fontFamily.font,
                    fontWeight = fontThickness.thickness,
                    textAlign = textAlignment.textAlignment,
                    textIndent = TextIndent(firstLine = paragraphIndentation),
                    fontStyle = fontStyle,
                    letterSpacing = letterSpacing,
                    fontSize = fontSize,
                    lineHeight = lineHeight,
                    color = fontColor,
                    lineBreak = LineBreak.Paragraph
                ),
                onDoubleClick = {
                    openTranslator(
                        ReaderEvent.OnOpenTranslator(
                            textToTranslate = paragraph.line.text,
                            translateWholeParagraph = true,
                            activity = activity
                        )
                    )
                },
                onClick = {
                    menuVisibility(
                        ReaderEvent.OnMenuVisibility(
                            show = !showMenu,
                            fullscreenMode = fullscreenMode,
                            saveCheckpoint = true,
                            activity = activity
                        )
                    )
                },
                toolbarHidden = toolbarHidden,
                highlightText = highlightedReading,
                highlightThickness = highlightedReadingThickness
            )
        }
        return
    }

    // Логика с формулами
    @OptIn(ExperimentalLayoutApi::class)
    Column(
        modifier = Modifier
            .animateItem(fadeInSpec = null, fadeOutSpec = null)
            .fillMaxWidth()
            .padding(horizontal = sidePadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = horizontalAlignment
    ) {
        fun isLongFormula(latex: String): Boolean {
            // Формулы с этими командами всегда выносим в отдельную строку
            val blockCommands = listOf(
                "\\frac", "\\sum", "\\int", "\\prod", "\\lim",
                "\\matrix", "\\pmatrix", "\\bmatrix", "\\cases",
                "\\begin{", "\\end{"
            )
            return blockCommands.any { latex.contains(it) } || latex.length > 20
        }

        // Анализируем структуру параграфа
        data class Segment(val content: String, val isFormula: Boolean, val isBlock: Boolean)
        val segments = mutableListOf<Segment>()
        var lastIndex = 0

        matches.forEach { match ->
            // Текст до формулы
            if (match.range.first > lastIndex) {
                val text = rawText.substring(lastIndex, match.range.first)
                if (text.isNotEmpty()) {
                    segments.add(Segment(text, false, false))
                }
            }

            // Формула
            val formula = match.groupValues[1]
            val isBlock = isLongFormula(formula)
            segments.add(Segment(formula, true, isBlock))

            lastIndex = match.range.last + 1
        }

        // Оставшийся текст
        if (lastIndex < rawText.length) {
            val text = rawText.substring(lastIndex)
            if (text.isNotEmpty()) {
                segments.add(Segment(text, false, false))
            }
        }

        // Группируем сегменты по блокам
        val blocks = mutableListOf<List<Segment>>()
        var currentBlock = mutableListOf<Segment>()

        segments.forEach { segment ->
            if (segment.isBlock) {
                // Сохраняем текущий блок если есть
                if (currentBlock.isNotEmpty()) {
                    blocks.add(currentBlock.toList())
                    currentBlock.clear()
                }
                // Добавляем блочную формулу как отдельный блок
                blocks.add(listOf(segment))
            } else {
                currentBlock.add(segment)
            }
        }

        // Добавляем последний блок
        if (currentBlock.isNotEmpty()) {
            blocks.add(currentBlock.toList())
        }

        // Рендерим блоки
        blocks.forEachIndexed { blockIndex, block ->
            if (block.size == 1 && block[0].isBlock) {
                // Блочная формула
                ReaderLayoutMath(
                    latex = block[0].content,
                    fontColor = fontColor,
                    fontSize = fontSize,
                    sidePadding = 0.dp,
                    inline = false,
                    textAlign = textAlignment.textAlignment
                )

                // Вертикальный отступ после блочной формулы
                if (blockIndex < blocks.size - 1) {
                    val density = androidx.compose.ui.platform.LocalDensity.current
                    Spacer(modifier = Modifier.height(with(density) { lineHeight.toDp() }))
                }
            } else {
                // Проверяем, есть ли в блоке формулы
                val hasFormulas = block.any { it.isFormula }

                if (!hasFormulas) {
                    // Простой текст без формул
                    BasicText(
                        text = block.joinToString("") { it.content },
                        style = TextStyle(
                            fontFamily = fontFamily.font,
                            fontWeight = fontThickness.thickness,
                            textAlign = textAlignment.textAlignment,
                            textIndent = if (blockIndex == 0) TextIndent(firstLine = paragraphIndentation) else TextIndent.None,
                            fontStyle = fontStyle,
                            letterSpacing = letterSpacing,
                            fontSize = fontSize,
                            lineHeight = lineHeight,
                            color = fontColor,
                            lineBreak = LineBreak.Paragraph
                        )
                    )
                } else {
                    // Комбинированный контент - используем FlowRow для правильного переноса
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = when (textAlignment.textAlignment) {
                            androidx.compose.ui.text.style.TextAlign.Center -> Arrangement.Center
                            androidx.compose.ui.text.style.TextAlign.End -> Arrangement.End
                            else -> Arrangement.Start
                        },
                        verticalArrangement = Arrangement.Center,
                        maxItemsInEachRow = Int.MAX_VALUE // Позволяем максимальное количество элементов в строке
                    ) {
                        // Объединяем последовательные текстовые сегменты
                        var i = 0
                        while (i < block.size) {
                            val segment = block[i]
                            if (!segment.isFormula) {
                                // Собираем все последовательные текстовые сегменты
                                val textBuilder = StringBuilder(segment.content)
                                var j = i + 1
                                while (j < block.size && !block[j].isFormula) {
                                    textBuilder.append(block[j].content)
                                    j++
                                }

                                // Рендерим объединенный текст
                                BasicText(
                                    text = textBuilder.toString(),
                                    style = TextStyle(
                                        fontFamily = fontFamily.font,
                                        fontWeight = fontThickness.thickness,
                                        fontStyle = fontStyle,
                                        letterSpacing = letterSpacing,
                                        fontSize = fontSize,
                                        lineHeight = lineHeight,
                                        color = fontColor
                                    )
                                )
                                i = j
                            } else {
                                // Рендерим формулу
                                ReaderLayoutMath(
                                    latex = segment.content,
                                    fontColor = fontColor,
                                    fontSize = fontSize,
                                    sidePadding = 0.dp,
                                    inline = true
                                )
                                i++
                            }
                        }
                    }
                }
            }
        }
    }
}