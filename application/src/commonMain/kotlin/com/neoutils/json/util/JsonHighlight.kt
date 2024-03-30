package com.neoutils.json.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

class JsonHighlight(
    private val keyColor: Color,
    private val stringColor: Color,
    private val numberColor: Color,
    private val booleanColor: Color,
    private val nullColor: Color
) {

    operator fun invoke(text: String): AnnotatedString {

        val contexts = mutableListOf<Context>()

        val tokens = Token.parse(text)

        val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()

        tokens.forEach { token ->

            val context = contexts.lastOrNull()

            when (token.type) {

                Token.Type.START_OBJECT -> {
                    contexts.add(Context.OBJECT_KEY)
                }

                Token.Type.START_ARRAY -> {
                    contexts.add(Context.ARRAY)
                }

                Token.Type.END_OBJECT -> {
                    contexts.removeLastOrNull()
                }

                Token.Type.END_ARRAY -> {
                    contexts.removeLastOrNull()
                }

                Token.Type.COLON -> {
                    contexts.add(Context.OBJECT_VALUE)
                }

                Token.Type.COMMA -> {
                    if (context == Context.OBJECT_VALUE) {
                        contexts.removeLastOrNull()
                    }
                }

                Token.Type.STRING -> {

                    if (context == Context.OBJECT_KEY) {
                        spanStyles.add(
                            AnnotatedString.Range(
                                SpanStyle(keyColor),
                                token.range.first,
                                token.range.last.inc()
                            )
                        )

                        return@forEach
                    }

                    if (context?.isValue == true) {
                        spanStyles.add(
                            AnnotatedString.Range(
                                SpanStyle(stringColor),
                                token.range.first,
                                token.range.last.inc()
                            )
                        )

                        return@forEach
                    }
                }

                Token.Type.NUMBER -> {
                    if (context?.isValue == true) {
                        spanStyles.add(
                            AnnotatedString.Range(
                                SpanStyle(numberColor),
                                token.range.first,
                                token.range.last.inc()
                            )
                        )
                    }
                }

                Token.Type.BOOLEAN -> {
                    if (context?.isValue == true) {
                        spanStyles.add(
                            AnnotatedString.Range(
                                SpanStyle(booleanColor),
                                token.range.first,
                                token.range.last.inc()
                            )
                        )
                    }
                }

                Token.Type.NULL -> {
                    if (context?.isValue == true) {
                        spanStyles.add(
                            AnnotatedString.Range(
                                SpanStyle(nullColor),
                                token.range.first,
                                token.range.last.inc()
                            )
                        )
                    }
                }

            }
        }

        return AnnotatedString(
            text = text,
            spanStyles = spanStyles
        )
    }

    enum class Context {
        OBJECT_KEY,
        OBJECT_VALUE,
        ARRAY;

        val isValue get() = this == OBJECT_VALUE || this == ARRAY
    }
}