package ru.musintimur.instastat.web.javascript.inputs

import org.w3c.dom.HTMLInputElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.API_PROFILE_ACTIVATE
import ru.musintimur.instastat.web.javascript.extensions.frmt
import ru.musintimur.instastat.web.javascript.extensions.setupPost

fun changeProfileActivity(checkbox: HTMLInputElement) {
    val params = "profileId=${checkbox.value}&isActive=${checkbox.checked}"

    val request = XMLHttpRequest()
    request.setupPost(API_PROFILE_ACTIVATE) {
        runCatching {
            val res = request.responseText
            println(res)
        }.onFailure {
            it.frmt()
        }
    }.send(params)
}