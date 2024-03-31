package com.neoutils.json.ui

import androidx.compose.ui.text.TextRange

fun TextRange.singleIndex(): Int {

    check(collapsed) { "TextRange must be collapsed" }

    return min
}