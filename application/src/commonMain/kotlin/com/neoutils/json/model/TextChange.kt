package com.neoutils.json.model

import androidx.compose.ui.text.input.TextFieldValue

data class TextChange(
    val newText: TextFieldValue,
    val oldText: TextFieldValue,
) {
    val diff = newText.text.length - oldText.text.length

    val isInsertion = newText.text.length > oldText.text.length
    val isDeletion = newText.text.length < oldText.text.length
    val isReplacement = newText.text != oldText.text && diff == 0

    val collapsed = newText.selection.collapsed && oldText.selection.collapsed
}
