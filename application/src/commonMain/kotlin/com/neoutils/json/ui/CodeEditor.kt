@file:OptIn(ExperimentalFoundationApi::class)

package com.neoutils.json.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
) {

    val mergedTextStyle = typography.body2.merge(textStyle)

    val scrollState = rememberScrollState()

    val lineCount = remember { mutableIntStateOf(0) }

    Row(modifier) {

        LineNumbers(
            count = lineCount.value,
            offset = scrollState.value,
            textStyle = TextStyle(
                lineHeight = mergedTextStyle.lineHeight,
                fontSize = mergedTextStyle.fontSize,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            ),
            modifier = Modifier.background(
                color = Color.LightGray.copy(alpha = 0.5f)
            ).fillMaxHeight()
        )

        // TODO: improve later, BasicTextField is not performant for large text
        BasicTextField(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f, false)
                .fillMaxSize()
                // TODO: improve later, https://github.com/JetBrains/compose-multiplatform/issues/4533
                .verticalScroll(scrollState),
            value = code,
            onValueChange = onCodeChange,
            textStyle = mergedTextStyle.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.Both
                )
            ),
            onTextLayout = {
                lineCount.value = it.lineCount
            },
        )
    }
}

@Composable
fun LineNumbers(
    count: Int,
    offset: Int,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {

    val lines = remember(count) {
        // TODO: improve later
        IntRange(1, count).joinToString("\n")
    }

    val scrollState = remember(offset) { ScrollState(offset) }

    BasicText(
        text = lines,
        style = textStyle.copy(
            textAlign = TextAlign.End,
        ),
        modifier = modifier.verticalScroll(
            state = scrollState,
            enabled = false
        ).padding(horizontal = 8.dp)
    )
}