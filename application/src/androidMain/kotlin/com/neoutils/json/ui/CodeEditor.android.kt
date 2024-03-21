package com.neoutils.json.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
actual fun CodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier,
    textStyle: TextStyle,
) {

    val mergedTextStyle = typography.body2.merge(textStyle)

    val scrollState = rememberScrollState()

    val lineCount = remember { mutableIntStateOf(0) }

    Row(modifier) {

        LineNumbers(
            count = lineCount.intValue,
            offset = scrollState.value,
            textStyle = TextStyle(
                lineHeight = mergedTextStyle.lineHeight,
                fontSize = mergedTextStyle.fontSize,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            modifier = Modifier
                .background(Color.LightGray.copy(alpha = 0.5f))
                .fillMaxHeight()
        )

        // TODO(improve): it's not performant for large text
        BasicTextField(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f, false)
                .fillMaxSize()
                // TODO(improve): https://github.com/JetBrains/compose-multiplatform/issues/4533
                .verticalScroll(scrollState),
            value = code,
            onValueChange = onCodeChange,
            textStyle = mergedTextStyle.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            onTextLayout = {
                lineCount.intValue = it.lineCount
            },
        )
    }
}