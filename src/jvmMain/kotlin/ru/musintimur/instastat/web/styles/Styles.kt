package ru.musintimur.instastat.web.styles

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.css.*
import ru.musintimur.instastat.common.constants.STYLES_COMMON

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

fun Route.styles() {
    get(STYLES_COMMON) {
        call.respondCss {

            body {
                fontFamily = "'PT Serif', sans-serif"
                position = Position.relative
            }
            images()
            sections()
        }
    }
}