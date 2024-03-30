package com.neoutils.json.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import cafe.adriel.voyager.core.model.ScreenModel
import com.neoutils.json.util.JsonHighlight
import com.neoutils.json.util.Token
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel : ScreenModel {

    private val jsonHighlight = JsonHighlight(
        keyColor = Color(0xffaa2727),
        stringColor = Color(0xff5589c2),
        numberColor = Color(0xff6ab69a),
        booleanColor = Color(0xffff842b),
        nullColor = Color(0xffe41500)
    )

    val code = MutableStateFlow("")

    val highlight = MutableStateFlow(listOf<AnnotatedString.Range<SpanStyle>>())

    fun onCodeChange(it: String) {

        code.value = it

        highlight.value = jsonHighlight(it).spanStyles
    }
}