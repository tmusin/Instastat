package ru.musintimur.instastat.web.javascript.inputs

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.get
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.web.javascript.extensions.asHTMLInputElement

fun setupInputs() {
    val inputs = document.getElementsByClassName(CLS_INPUT)
    for (i in 0..inputs.length) {
        inputs[i]?.asHTMLInputElement()?.let { element ->
            when {
                element.classList.contains(CLS_INPUT_DICTIONARY_ENTRY) -> {
                    element.onchange = {
                        changeProfileActivity(element)
                    }
                }
                element.classList.contains(CLS_INPUT_POST_ENTRY) -> {
                    element.onclick = {
                        startPostParsing(element)
                    }
                }
                element.id in setOf(INPUT_ID_BUTTON_ADD_PROFILE, INPUT_ID_BUTTON_ADD_POST) -> {
                    element.onclick = {
                        window.location.replace("${window.location}/add")
                    }
                }
            }
        }
    }
}