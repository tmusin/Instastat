package ru.musintimur.instastat.extensions

import kotlinx.html.LINK
import kotlinx.html.SCRIPT
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.ATTR_CROSS_ORIGIN

fun LINK.crossOrigin(newValue: String) {
    StringAttribute()[this, ATTR_CROSS_ORIGIN] = newValue
}

fun SCRIPT.crossOrigin(newValue: String) {
    StringAttribute()[this, ATTR_CROSS_ORIGIN] = newValue
}