package ru.musintimur.instastat.web.javascript.inputs

import org.w3c.dom.HTMLInputElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.API_PRINT_COMMENTS
import ru.musintimur.instastat.web.javascript.extensions.frmt
import ru.musintimur.instastat.web.javascript.extensions.setupPost

fun printComments(button: HTMLInputElement) {
    val params = "postId=${button.getAttribute("post_id")}"

    val request = XMLHttpRequest()
    request.setupPost(API_PRINT_COMMENTS) {
        runCatching {
            val res = request.responseText
            println(res)
        }.onFailure {
            it.frmt()
        }
    }.send(params)
}