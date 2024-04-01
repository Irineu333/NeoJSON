package com.neoutils.json.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.neoutils.json.model.TextChange
import com.neoutils.json.ui.singleIndex
import kotlin.math.abs

class AutoComplete(
    indent: Int
) {

    private val defaultIndent = ' ' * indent

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

        val newText = textChange.newText

        val insertedIndex = newText.selection.singleIndex() - 1
        val insertedChar = newText.text[insertedIndex]

        Token.entries.find {
            it.start == insertedChar
        }?.let { token ->
            return newText.let {

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

        // auto indent

        if (insertedChar == '\n') {

            val previousIndex = insertedIndex - 1
            val previousChar = newText.text.getOrNull(previousIndex)

            val nextIndex = insertedIndex + 1
            val nextChar = newText.text.getOrNull(nextIndex)

            val previousText = newText.text.substring(
                startIndex = 0,
                endIndex = insertedIndex
            )

            val previousIndent = previousText.lastIndent()

            val copiedIndent = ' ' * previousIndent

            listOf(Token.OBJECT, Token.ARRAY).find {
                it.start == previousChar && it.end == nextChar
            }?.let {
                return newText.let {

                    val text = it.text

                    val newIndent = copiedIndent +
                            defaultIndent + '\n' +
                            copiedIndent

                    it.copy(
                        text = text.substring(
                            startIndex = 0,
                            endIndex = insertedIndex + 1
                        ) + newIndent + text.substring(
                            startIndex = nextIndex,
                            endIndex = text.length
                        ),
                        selection = TextRange(
                            index = nextIndex +
                                    copiedIndent.length +
                                    defaultIndent.length
                        )
                    )
                }
            }

            return newText.copy(
                text = newText.text.substring(
                    startIndex = 0,
                    endIndex = insertedIndex + 1
                ) + copiedIndent + newText.text.substring(
                    startIndex = nextIndex,
                    endIndex = newText.text.length
                ),
                selection = TextRange(
                    index = insertedIndex + previousIndent + 1
                )
            )
        }

        return newText
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
