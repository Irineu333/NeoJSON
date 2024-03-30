package com.neoutils.json.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.neoutils.json.util.JsonHighlight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ScreenModel {

    private val jsonHighlight = JsonHighlight(
        keyColor = Color(0xffaa2727),
        stringColor = Color(0xff5589c2),
        numberColor = Color(0xff6ab69a),
        booleanColor = Color(0xffff842b),
        nullColor = Color(0xffe41500)
    )

    private var highlightJob: Job? = null

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _highlight = MutableStateFlow(listOf<AnnotatedString.Range<SpanStyle>>())
    val highlight = _highlight.asStateFlow()

    fun onCodeChange(code: String) {

        _code.value = code

        updateHighlight(code)
    }

    private fun updateHighlight(text: String) {

        highlightJob?.cancel()
        highlightJob = screenModelScope.launch(Dispatchers.IO) {
            _highlight.value = jsonHighlight(text).spanStyles
        }
    }
}