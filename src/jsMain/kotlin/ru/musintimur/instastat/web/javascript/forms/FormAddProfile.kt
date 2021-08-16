package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.ADD_PROFILE_MESSAGE
import ru.musintimur.instastat.common.constants.API_ADD_PROFILE
import ru.musintimur.instastat.web.javascript.extensions.collectParameters
import ru.musintimur.instastat.web.javascript.extensions.frmt
import ru.musintimur.instastat.web.javascript.extensions.setupPost

fun onProfileSubmit(form: HTMLFormElement) {
    val messageBlock = document.getElementById(ADD_PROFILE_MESSAGE)
    val data = form.collectParameters()

    val request = XMLHttpRequest()
    request.setupPost(API_ADD_PROFILE) {
        runCatching {
            if (request.status.toInt() == 200) {
                window.location.href = window.location.href.substringBefore("/add")
            } else {
                messageBlock?.innerHTML = request.responseText
            }
        }.onFailure { e ->
            e.frmt()
            e.message?.let { messageBlock?.innerHTML = it }
        }
    }.send(data)
}