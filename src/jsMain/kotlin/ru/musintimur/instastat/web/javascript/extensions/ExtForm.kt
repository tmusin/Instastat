package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get

fun HTMLFormElement.collectParameters(): String {
    var params = ""
    for (i in 0 until this.elements.length) {
        when (val element = this.elements[i]) {
            is HTMLInputElement -> {
                params += element.params()
            }
        }
    }
    return params.dropLast(1)
}