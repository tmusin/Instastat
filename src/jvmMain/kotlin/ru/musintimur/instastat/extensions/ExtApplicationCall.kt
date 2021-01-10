package ru.musintimur.instastat.extensions

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.stream.appendHTML

suspend fun ApplicationCall.respondDiv(
    divBlock: DIV.()->Unit,
    outerId: String,
    contentType: ContentType? = null,
    status: HttpStatusCode? = null,
    configure: OutgoingContent.() -> Unit = {}) {
    val htmlBlock = buildString {
        appendHTML().div {
            id = outerId
            divBlock()
        }
    }
    val message = TextContent(htmlBlock, defaultTextContentType(contentType), status).apply(configure)
    respond(message)
}