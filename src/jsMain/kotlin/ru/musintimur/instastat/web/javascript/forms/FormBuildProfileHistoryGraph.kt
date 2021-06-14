package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLFormElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.API_GRAPH_BLOCK
import ru.musintimur.instastat.common.constants.STAT_GRAPH_BLOCK_OUTER
import ru.musintimur.instastat.web.javascript.extensions.applyUrlParams
import ru.musintimur.instastat.web.javascript.extensions.collectParameters
import ru.musintimur.instastat.web.javascript.extensions.setupGet
import ru.musintimur.instastat.web.javascript.setupAll

fun onSubmitProfileHistoryGraph(form: HTMLFormElement) {
    val reportBlock: HTMLDivElement = document.getElementById(STAT_GRAPH_BLOCK_OUTER) as HTMLDivElement

    val data: String = form.collectParameters()
    val url = API_GRAPH_BLOCK.applyUrlParams(data)

    val request = XMLHttpRequest()
    request.setupGet(url) {
        reportBlock.innerHTML = request.responseText
        setupAll()
    }.send()
}