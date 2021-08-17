package ru.musintimur.instastat.web.javascript.forms

import kotlinx.browser.document
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.get
import ru.musintimur.instastat.common.constants.*

fun setupForms() {
    val forms = document.forms
    for (i in 0 until forms.length) {
        forms[i]?.let { item ->
            val form = item as HTMLFormElement
            when(form.id) {
                FORM_ID_REPORT -> {
                    form.onsubmit = { event ->
                        onSubmitFormStatReport(form)
                        event.preventDefault()
                    }
                }
                FORM_ID_PARSING -> {
                    form.onsubmit = { event ->
                        setupFormStartParsing(form)
                        event.preventDefault()
                    }
                }
                FORM_ID_GRAPH -> {
                    form.onsubmit = { event ->
                        onSubmitProfileHistoryGraph(form)
                        event.preventDefault()
                    }
                }
                FORM_ID_ADD_PROFILE -> {
                    form.onsubmit = { event ->
                        onProfileSubmit(form)
                        event.preventDefault()
                    }
                }
                FORM_ID_ADD_POST -> {
                    form.onsubmit = { event ->
                        onPostSubmit(form)
                        event.preventDefault()
                    }
                }
            }
        }
    }
}