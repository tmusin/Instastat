package ru.musintimur.instastat.extensions

import kotlinx.html.INPUT
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.ATTR_DATA_DATE_FORMAT
import ru.musintimur.instastat.common.constants.ATTR_DATA_PROVIDE

fun INPUT.dataProvide(value: String) {
    StringAttribute()[this, ATTR_DATA_PROVIDE] = value
}

fun INPUT.dataDateFormat(value: String) {
    StringAttribute()[this, ATTR_DATA_DATE_FORMAT] = value
}