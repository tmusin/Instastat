package ru.musintimur.instastat.extensions

import kotlinx.html.TD
import kotlinx.html.span
import kotlinx.html.style

fun TD.setColoredCell(count: Int, diff: Int) {
    + "$count ("
    coloredSpan(diff)
    + ")"
}

fun TD.coloredSpan(count: Int) {
    span {
        style = when {
            count > 0 -> "color:green"
            count < 0 -> "color:red"
            else -> "color:black"
        }
        +"$count"
    }
}