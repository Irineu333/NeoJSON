package com.neoutils.json.util

data class Token(
    val type: Type,
    val value: String,
    val range: IntRange
) {

    enum class Type(val regex: Regex) {
        STRING(Regex("\"[^\"]*\"")),
        START_OBJECT(Regex("\\{")),
        END_OBJECT(Regex("\\}")),
        START_ARRAY(Regex("\\[")),
        END_ARRAY(Regex("]")),
        NUMBER(Regex("-?\\d+(\\.\\d+)?")),
        BOOLEAN(Regex("true|false")),
        NULL(Regex("null")),
        COLON(Regex(":")),
        COMMA(Regex(","));
    }

    companion object {

        fun parse(value: String): List<Token> {

            val tokens = mutableListOf<Token>()

            val regex = Token.Type.entries.joinToString(
                separator = "|"
            ) {
                "(${it.regex.pattern})"
            }.toRegex()

            for (match in regex.findAll(value)) {

                val type = Token.Type.entries.first {
                    it.regex.matches(match.value)
                }

                tokens.add(
                    Token(
                        type,
                        match.value,
                        match.range
                    )
                )
            }

            return tokens
        }
    }
}