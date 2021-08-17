package ru.musintimur.instastat.web.styles

import kotlinx.css.CSSBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.marginTop
import ru.musintimur.instastat.common.constants.CLS_ACCOUNTS_SECTION
import ru.musintimur.instastat.common.constants.CLS_CANVAS_SECTION
import ru.musintimur.instastat.common.constants.CLS_POSTS_SECTION
import ru.musintimur.instastat.common.constants.CLS_REPORTS_SECTION

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
}