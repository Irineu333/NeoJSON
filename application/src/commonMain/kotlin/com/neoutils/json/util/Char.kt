package com.neoutils.json.util

operator fun Char.times(indent: Int): String {
    return IntRange(1, indent).joinToString(separator = "") { toString() }
}