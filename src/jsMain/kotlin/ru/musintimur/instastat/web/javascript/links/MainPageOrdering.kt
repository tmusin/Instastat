package ru.musintimur.instastat.web.javascript.links

import kotlinx.browser.document
import org.w3c.dom.*
import ru.musintimur.instastat.common.constants.FORM_ID_REPORT
import ru.musintimur.instastat.web.javascript.forms.onSubmitFormStatReport

fun onClickSortReport(ref: HTMLAnchorElement) {
    val form = document.forms[FORM_ID_REPORT] as HTMLFormElement
    val hidden = form.elements["sortOrdering"] as HTMLInputElement
    val link = ref.children["sortOrder"] as HTMLInputElement
    hidden.value = link.value
    onSubmitFormStatReport(form)
}