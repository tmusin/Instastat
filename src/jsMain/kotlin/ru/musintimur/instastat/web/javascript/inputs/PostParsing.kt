package ru.musintimur.instastat.web.javascript.inputs

import org.w3c.dom.HTMLInputElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.API_PARSE_POST
import ru.musintimur.instastat.web.javascript.extensions.frmt
import ru.musintimur.instastat.web.javascript.extensions.setupPost

fun startPostParsing(button: HTMLInputElement) {
    val params = "postUrl=${button.id}"

    val request = XMLHttpRequest()
    request.setupPost(API_PARSE_POST) {
        runCatching {
            val res = request.responseText
            println(res)
        }.onFailure {
            it.frmt()
        }
    }.send(params)
}