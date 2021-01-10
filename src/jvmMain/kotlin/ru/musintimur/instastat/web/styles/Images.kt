package ru.musintimur.instastat.web.styles

import kotlinx.css.CSSBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.height
import kotlinx.css.width
import ru.musintimur.instastat.common.constants.CLS_MINI_ICON

fun CSSBuilder.images() {
    rule(".$CLS_MINI_ICON") {
        val iconSize = 20
        width = LinearDimension("${iconSize}px")
        height = LinearDimension("${iconSize}px")
    }
}