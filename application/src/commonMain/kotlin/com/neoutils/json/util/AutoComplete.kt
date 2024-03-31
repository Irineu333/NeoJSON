package com.neoutils.json.util

import androidx.compose.ui.text.input.TextFieldValue
import com.neoutils.json.model.TextChange
import com.neoutils.json.ui.singleIndex
import kotlin.math.abs

object AutoComplete {

    operator fun invoke(
        newText: TextFieldValue,
        oldText: TextFieldValue
    ) = invoke(TextChange(newText, oldText))

    operator fun invoke(
        textChange: TextChange
    ): TextFieldValue {

        if (abs(textChange.diff) != 1) {
            return textChange.newText
        }

        if (!textChange.collapsed) {
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
        }?.let { token ->
            return textChange.newText.let {

                val text = it.text

                it.copy(
                    text = text.substring(
                        startIndex = 0,
                        endIndex = insertedIndex + 1
                    ) + token.end + text.substring(
                        startIndex = insertedIndex + 1,
                        endIndex = text.length
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

        // auto delete

        val oldText = textChange.oldText
        val newText = textChange.newText

        val deletedIndex = oldText.selection.singleIndex() - 1
        val deletedChar = oldText.text[deletedIndex]

        Token.entries.find {
            it.start == deletedChar
        }?.let { token ->

            val nextIndex = newText.selection.singleIndex()
            val nextChar = newText.text.getOrNull(nextIndex)

            if (nextChar != token.end) return@let

            return newText.let {

                val text = it.text

                it.copy(
                    text = text.substring(
                        startIndex = 0,
                        endIndex = nextIndex
                    ) + text.substring(
                        startIndex = nextIndex + 1,
                        endIndex = text.length
                    )
                )
            }
        }


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
