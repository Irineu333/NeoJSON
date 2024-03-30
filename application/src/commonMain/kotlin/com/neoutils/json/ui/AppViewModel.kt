package com.neoutils.json.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel : ScreenModel {

    val code = MutableStateFlow("")

    val highlight = MutableStateFlow(listOf<AnnotatedString.Range<SpanStyle>>())

    fun onCodeChange(it: String) {
        code.value = it
    }
}