package com.neoutils.json.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.neoutils.json.util.AutoComplete
import com.neoutils.json.util.JsonHighlight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ScreenModel {

    private val jsonHighlight = JsonHighlight(
        keyColor = Color(0xffaa2727),
        stringColor = Color(0xff5589c2),
        numberColor = Color(0xff6ab69a),
        booleanColor = Color(0xffff842b),
        nullColor = Color(0xffe41500)
    )

    private val _text = MutableStateFlow(TextFieldValue())
    val text = _text.asStateFlow()

    fun onCodeChange(code: TextFieldValue) {
        _text.value = AutoComplete(code, text.value).let {
            it.copy(jsonHighlight(it.text))
        }
    }
}