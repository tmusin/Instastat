package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.events.Event
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.API_DAY_REPORT
import ru.musintimur.instastat.common.constants.CLS_BOOTSTRAP_DATE_PICKER
import ru.musintimur.instastat.common.constants.STAT_REPORT_BLOCK_OUTER
import ru.musintimur.instastat.web.javascript.extensions.applyUrlParams
import ru.musintimur.instastat.web.javascript.extensions.asHTMLInputElement
import ru.musintimur.instastat.web.javascript.extensions.collectParameters
import ru.musintimur.instastat.web.javascript.extensions.setupGet

fun setupFormStatReport(form: HTMLFormElement) {
    val date = form.getElementsByClassName(CLS_BOOTSTRAP_DATE_PICKER)[0]?.asHTMLInputElement()?.value
    if (date.isNullOrBlank()) return

    val reportBlock: HTMLDivElement = document.getElementById(STAT_REPORT_BLOCK_OUTER) as HTMLDivElement

    val data: String = form.collectParameters()
    val url = API_DAY_REPORT.applyUrlParams(data)

    val request = XMLHttpRequest()
    request.setupGet(url) {
        reportBlock.innerHTML = request.responseText
    }.send()
}