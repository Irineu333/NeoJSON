package com.neoutils.json.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.rememberTextFieldVerticalScrollState
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
) {

    val mergedTextStyle = typography.body1.merge(textStyle)

    val scrollState = rememberTextFieldVerticalScrollState()

    val scrollAdapter = rememberScrollbarAdapter(scrollState)

    val lineCount = remember { mutableIntStateOf(0) }

    val lines = remember(lineCount.value) { (1..lineCount.value).joinToString("\n") }

    Row(modifier) {

        BasicText(
            text = lines,
            style = TextStyle(
                lineHeight = mergedTextStyle.lineHeight,
                fontSize = mergedTextStyle.fontSize,
                textAlign = TextAlign.End,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            ),
            modifier = Modifier.verticalScroll(
                state = ScrollState(
                    initial = scrollState.offset.toInt()
                ),
                enabled = false
            ).padding(horizontal = 8.dp)
        )

        BasicTextField(
            modifier = Modifier.weight(1f, false).fillMaxSize(),
            value = code,
            onValueChange = onCodeChange,
            scrollState = scrollState,
            textStyle = mergedTextStyle.copy(
                lineBreak = LineBreak.Simple,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.Both
                )
            ),
            onTextLayout = {
                lineCount.value = it.lineCount
            },
        )

        VerticalScrollbar(scrollAdapter)
    }
}