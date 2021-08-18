package ru.musintimur.instastat.web.styles

import kotlinx.css.CSSBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.marginTop
import ru.musintimur.instastat.common.constants.*

fun CSSBuilder.sections() {
    val topMargin = 70
    rule(".$CLS_REPORTS_SECTION") {
        marginTop = LinearDimension("${topMargin}px")
    }
    rule(".$CLS_CANVAS_SECTION") {
        marginTop = LinearDimension("${topMargin}px")
    }
    rule(".$CLS_ACCOUNTS_SECTION") {
        marginTop = LinearDimension("${topMargin}px")
    }
    rule(".$CLS_POSTS_SECTION") {
        marginTop = LinearDimension("${topMargin}px")
    }
    rule(".$CLS_COMMENTS_SECTION") {
        marginTop = LinearDimension("${topMargin}px")
    }
}