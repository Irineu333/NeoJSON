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

fun String.insertBetween(text: String, start: Int, end: Int): String {
    return substring(0, start) + text + substring(end, length)
}

fun String.insertBetween(text: String, index: Int): String {
    return insertBetween(text, index, index)
}