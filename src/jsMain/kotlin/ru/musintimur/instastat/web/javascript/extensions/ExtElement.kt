package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.Element
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLParagraphElement

fun Element.asHTMLInputElement(): HTMLInputElement? = runCatching {
    this as HTMLInputElement
}.onFailure {
    it.frmt()
}.getOrNull()

fun Element.asHTMLAnchorElement(): HTMLAnchorElement? = runCatching {
    this as HTMLAnchorElement
}.onFailure {
    it.frmt()
}.getOrNull()

fun Element.asHTMLParagraphElement(): HTMLParagraphElement? = runCatching {
    this as HTMLParagraphElement
}.onFailure {
    it.frmt()
}.getOrNull()