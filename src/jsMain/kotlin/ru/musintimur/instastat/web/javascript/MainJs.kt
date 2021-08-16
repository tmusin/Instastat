package ru.musintimur.instastat.web.javascript

import kotlinx.browser.window
import ru.musintimur.instastat.web.javascript.canvas.setupCanvas
import ru.musintimur.instastat.web.javascript.forms.setupForms
import ru.musintimur.instastat.web.javascript.inputs.setupInputs
import ru.musintimur.instastat.web.javascript.links.setupLinks

fun main() {
    window.onload = {
        setupAll()
    }
}

fun setupAll() {
    setupForms()
    setupCanvas()
    setupLinks()
    setupInputs()
}