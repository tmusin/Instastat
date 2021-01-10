package ru.musintimur.instastat.common.extensions

val APOSTROPHE = Pair("&#39;", "'")
val AMPERSAND = Pair("&amp;", "&")
val QUOTE = Pair("&quot;", "\"")

fun String.replaceHtmlMnemonics(): String =
    this.replaceHtmlApostrophe()
        .replaceHtmlAmpersand()
        .replaceHtmlQuote()

fun String.replaceHtmlApostrophe(): String =
    this.replace(APOSTROPHE.first, APOSTROPHE.second)

fun String.replaceHtmlAmpersand(): String =
    this.replace(AMPERSAND.first, AMPERSAND.second)

fun String.replaceHtmlQuote(): String =
    this.replace(QUOTE.first, QUOTE.second)

fun String.removeSpace(): String = this.replace(" ", "")

fun String.removeComma(): String = this.replace(",", "")

fun String.parseLong(): Long = runCatching {
    this.removeSpace().removeComma().toLong()
}.getOrThrow()