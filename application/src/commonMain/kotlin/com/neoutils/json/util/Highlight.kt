package com.neoutils.json.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

@Composable
fun rememberHighlight(
    text: String,
    highlight: List<AnnotatedString.Range<SpanStyle>>
) = remember(text, highlight) {
    text.withHighlight(highlight)
}