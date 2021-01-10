package ru.musintimur.instastat.web.javascript

import kotlinx.browser.window
import ru.musintimur.instastat.web.javascript.canvas.setupCanvas
import ru.musintimur.instastat.web.javascript.forms.setupForms

fun main() {
    window.onload = {
        setupForms()
        setupCanvas()
    }
}