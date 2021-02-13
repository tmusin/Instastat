package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.HTMLTextAreaElement

fun HTMLTextAreaElement.params(): String {
    val common: Boolean = this.name.isNotBlank() && this.value.isNotBlank()
    return if (common) {
        "${this.name}=${this.value}&"
    } else {
        ""
    }
}