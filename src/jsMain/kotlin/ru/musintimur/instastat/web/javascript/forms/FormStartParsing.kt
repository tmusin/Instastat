package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.document
import kotlinx.coroutines.*
import org.w3c.dom.*
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.common.messages.ParserProgress
import ru.musintimur.instastat.web.javascript.extensions.setupGet
import ru.musintimur.instastat.web.javascript.extensions.setupPost

fun setupFormStartParsing(form: HTMLFormElement) {
    val url = API_START_PARSING
    val progressBar = document.getElementsByClassName(CLS_PROGRESS_PARSING)[0] as HTMLDivElement
    val button = form.getElementsByTagName("input")[0] as HTMLInputElement
    progressBar.style.visibility = "visible"
    button.style.display = "none"
    val job = CoroutineScope(Dispatchers.Default)
    val request = XMLHttpRequest()
    animateParserProgressBar(progressBar, job)
    request.setupPost(url) {
        progressBar.style.visibility = "hidden"
        job.cancel()
    }.send()
}

private fun animateParserProgressBar(progressBar: HTMLDivElement, job: CoroutineScope) {
    val bar = progressBar.getElementsByClassName(CLS_PROGRESS_BAR)[0] as HTMLDivElement
    var progress = 0
    job.launch {
        while (progress < 100) {
            val request = XMLHttpRequest()
            request.setupGet(API_PARSE_PROGRESS) {
                val result = request.responseText
                progress = JSON.parse<ParserProgress>(result).progress
                bar.style.width = "$progress%"
                bar.setAttribute(ATTR_ARIA_VALUE_NOW, progress.toString())
            }.send()
            delay(1000)
        }
    }
}