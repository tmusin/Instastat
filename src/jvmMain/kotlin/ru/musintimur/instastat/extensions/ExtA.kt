package ru.musintimur.instastat.extensions

import kotlinx.html.A
import kotlinx.html.attributes.StringAttribute

fun A.dataToggle(value: String) {
    StringAttribute()[this, "data-toggle"] = value
}

fun A.ariaControls(value: String) {
    StringAttribute()[this, "aria-controls"] = value
}