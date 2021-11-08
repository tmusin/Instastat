package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.AUTH_API
import ru.musintimur.instastat.common.constants.PARAGRAPH_ERROR
import ru.musintimur.instastat.web.javascript.extensions.asHTMLParagraphElement
import ru.musintimur.instastat.web.javascript.extensions.collectParameters
import ru.musintimur.instastat.web.javascript.extensions.setupPost

fun setupFormAuthenticate(form: HTMLFormElement) {
    val data: dynamic = form.collectParameters()
    val errorParagraph = form.getElementsByClassName(PARAGRAPH_ERROR)[0]?.asHTMLParagraphElement()

    val request = XMLHttpRequest()
    request.setupPost(AUTH_API) {
        when (val status = request.status.toInt()) {
            200 -> window.location.href = "/"
            else -> errorParagraph?.innerText = "$status: ${request.responseText}"
        }
    }.send(data)
}