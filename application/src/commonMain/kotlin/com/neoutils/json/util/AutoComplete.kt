package com.neoutils.json.util

import androidx.compose.ui.text.input.TextFieldValue
import com.neoutils.json.model.TextChange
import com.neoutils.json.ui.singleIndex

object AutoComplete {

    operator fun invoke(
        newText: TextFieldValue,
        oldText: TextFieldValue
    ) = invoke(TextChange(newText, oldText))

    operator fun invoke(
        textChange: TextChange
    ): TextFieldValue {

        if (textChange.diff != 1) {
            return textChange.newText
        }

        if (textChange.isInsertion) {
            return onInsert(textChange)
        }

        if (textChange.isDeletion) {
            return onDelete(textChange)
        }

        throw RuntimeException()
    }

    private fun onInsert(
        textChange: TextChange
    ): TextFieldValue {

        // auto close

        val insertedIndex = textChange.newText.selection.singleIndex() - 1
        val insertedChar = textChange.newText.text[insertedIndex]

        Token.entries.find {
            it.start == insertedChar
        }?.also { token ->
            return textChange.newText.let {

                val text = it.text

                it.copy(
                    text = text.insertBetween(
                        index = insertedIndex + 1,
                        text = "${token.end}",
                    )
                )
            }
        }

        // TODO: implement auto indent

        return textChange.newText
    }

    private fun onDelete(
        textChange: TextChange
    ): TextFieldValue {

        // TODO: implement auto delete

        return textChange.newText
    }

    enum class Token(
        val start: Char,
        val end: Char
    ) {
        ARRAY('[', ']'),
        OBJECT('{', '}'),
        STRING('"', '"')
    }
}
