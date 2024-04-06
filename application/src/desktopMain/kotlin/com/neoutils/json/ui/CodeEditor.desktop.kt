package com.neoutils.json.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.rememberTextFieldVerticalScrollState
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun CodeEditor(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier,
    textStyle: TextStyle,
) {

    val mergedTextStyle = typography.body2.merge(textStyle)

    val scrollState = rememberTextFieldVerticalScrollState()

    val scrollbarAdapter = rememberScrollbarAdapter(scrollState)

    val offset = remember(scrollState.offset) { scrollState.offset.roundToInt() }

    val lineCount = remember { mutableIntStateOf(1) }

    Row(modifier) {

        LineNumbers(
            count = lineCount.value,
            offset = offset,
            textStyle = TextStyle(
                lineHeight = mergedTextStyle.lineHeight,
                fontSize = mergedTextStyle.fontSize,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            modifier = Modifier.background(
                color = Color.LightGray.copy(alpha = 0.5f)
            ).fillMaxHeight()
        )

        val lineHeight = with(LocalDensity.current) { mergedTextStyle.lineHeight.toPx() }

        val visibleLinesCount = scrollState.viewportSize / lineHeight

        val firstVisibleLine = (offset / lineHeight).roundDown()

        val lastVisibleLine = (firstVisibleLine + visibleLinesCount).roundUp()

        val splitWithRange = remember(value.text) {
            value.text.splitWithRange("\n")
        }

        val textIndexRange = splitWithRange.subList(
            firstVisibleLine,
            minOf(lastVisibleLine, splitWithRange.size)
        )

        // TODO(improve): it's not performant for large text
        BasicTextField(
            value = value.copy(
                composition = null,
                annotatedString = AnnotatedString(
                    text = value.text,
                    spanStyles = value.annotatedString.spanStyles.filter { spanStyle ->
                        textIndexRange.any {
                            spanStyle.start in it && spanStyle.end in it
                        }
                    }
                )
            ),
            scrollState = scrollState,
            onValueChange = onValueChange,
            textStyle = mergedTextStyle.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            onTextLayout = {
                lineCount.value = it.lineCount
            },
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f, false)
                .fillMaxSize(),
        )

        VerticalScrollbar(scrollbarAdapter)
    }
}

private fun Float.roundUp(): Int {
    return toInt() + 1
}

private fun Float.roundDown(): Int {
    return toInt()
}

private fun String.splitWithRange(delimiter: String): List<IntRange> {

    val result = mutableListOf<IntRange>()

    var start = 0
    var end: Int

    while (this.indexOf(delimiter, start).also { end = it } != -1) {
        result.add(start..<end + 1)
        start = end + delimiter.length
    }

    result.add(start..<length + 1)

    return result
}
