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

fun String.lastIndent(): Int {

    if (isEmpty()) return 0

    var index = lastIndex

    var char = get(index)

    var count = 0

    while (char != '\n' && index != -1) {

        if (char == ' ') {
            count++
        } else {
            count = 0
        }

        char = get(index--)
    }

    return count
}