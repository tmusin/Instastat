package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.document
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.get
import ru.musintimur.instastat.common.constants.CLS_FORM_DATE_PICKER
import ru.musintimur.instastat.common.constants.CLS_FORM_START_PARSING

fun setupForms() {
    val forms = document.forms
    for (i in 0 until forms.length) {
        forms[i]?.let { item ->
            val form = item as HTMLFormElement
            when {
                form.classList.contains(CLS_FORM_DATE_PICKER) -> {
                    form.onsubmit = { event ->
                        onSubmitFormStatReport(form)
                        event.preventDefault()
                    }
                }
                form.classList.contains(CLS_FORM_START_PARSING) -> {
                    form.onsubmit = { event ->
                        setupFormStartParsing(form)
                        event.preventDefault()
                    }
                }
            }
        }
    }
}