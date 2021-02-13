package ru.musintimur.instastat.extensions

import kotlinx.html.BUTTON
import kotlinx.html.attributes.StringAttribute

fun BUTTON.dropdownStyle() {
    StringAttribute()[this, "data-toggle"] = "dropdown"
    StringAttribute()[this, "aria-haspopup"] = "true"
    StringAttribute()[this, "aria-expanded"] = "false"
}