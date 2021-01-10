package ru.musintimur.instastat.extensions

import kotlinx.html.LI
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.ATTR_DATA_TARGET
import ru.musintimur.instastat.common.constants.ATTR_DATA_TOGGLE

fun LI.dataToggle(value: String) {
    StringAttribute()[this, ATTR_DATA_TOGGLE] = value
}

fun LI.dataTarget(value: String) {
    StringAttribute()[this, ATTR_DATA_TARGET] = value
}