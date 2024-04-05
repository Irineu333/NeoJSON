package com.neoutils.json.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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

    private var highlight = listOf<AnnotatedString.Range<SpanStyle>>()

    private val autoComplete = AutoComplete(indent = 4)

    private val _textField = MutableStateFlow(TextFieldValue())
    val textField = _textField.asStateFlow()

    fun onValueChange(newTextField: TextFieldValue) {

        if (newTextField.text != textField.value.text) {

            _textField.value = autoComplete(
                newTextField, textField.value
            ).let {
                // TODO(improve): make asynchronous
                highlight = jsonHighlight(it.text).spanStyles
                it.copy(AnnotatedString(it.text, highlight))
            }

            return
        }

        _textField.value = newTextField.copy(
            AnnotatedString(
                newTextField.text,
                highlight
            )
        )
    }
}