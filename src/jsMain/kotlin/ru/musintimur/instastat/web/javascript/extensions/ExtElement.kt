package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement

fun Element.asHTMLInputElement(): HTMLInputElement? = runCatching {
    this as HTMLInputElement
}.onFailure {
    it.frmt()
}.getOrNull()