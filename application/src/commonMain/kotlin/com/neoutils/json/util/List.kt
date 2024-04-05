package com.neoutils.json.util

enum class Position {
    SINGLE,
    FIRST,
    LAST,
    BETWEEN
}

inline fun <E> List<E>.forEachWithPosition(
    action: (Position, E) -> Unit
) {
    require(isNotEmpty()) { "List is empty" }

    for ((index, element) in withIndex()) {

        val position = when {
            size == 1 -> Position.SINGLE
            index == 0 -> Position.FIRST
            index == lastIndex -> Position.LAST
            else -> Position.BETWEEN
        }

        action(position, element)
    }
}
