package ru.musintimur.instastat.extensions

import kotlinx.html.CANVAS
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.ATTR_PROFILE_NAME

fun CANVAS.profileName(value: String) {
    StringAttribute()[this, ATTR_PROFILE_NAME] = value
}