package com.neoutils.json.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

fun String.withHighlight(
    highlight: List<AnnotatedString.Range<SpanStyle>>
): AnnotatedString {
    return AnnotatedString(
        text = this,
        spanStyles = highlight.filter {
            it.start <= length && it.end <= length
        }
    )
}