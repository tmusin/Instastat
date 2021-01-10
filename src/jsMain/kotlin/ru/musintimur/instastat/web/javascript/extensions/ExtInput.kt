package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.HTMLInputElement

fun HTMLInputElement.params(): String {
    val common: Boolean = this.name.isNotBlank() && this.value.isNotBlank()
    val special: Boolean = when(this.type) {
        "checkbox" -> this.checked
        else -> true
    }
    return if (common && special) {
        "${this.name}=${this.value.encodeUrlSymbols()}&"
    } else {
        ""
    }
}

private val symbolsMap = mapOf (
    '%' to "%25", //always first!!!
    '+' to "%2B",
    '/' to "%2F",
    '&' to "%26",
    '?' to "%3F",
    ' ' to "+" //always last!!!
)

fun String.encodeUrlSymbols(): String {
    var newString = this
    symbolsMap.forEach { (t, u) ->
        newString = newString.replace(t.toString(), u)
    }
    return newString
}

fun String.applyUrlParams(parameters: String): String {
    val params = if (parameters.isNotBlank()) "?$parameters" else ""

    return "$this$params"
}