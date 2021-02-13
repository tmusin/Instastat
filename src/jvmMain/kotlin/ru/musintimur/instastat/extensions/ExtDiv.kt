package ru.musintimur.instastat.extensions

import kotlinx.html.DIV
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.ATTR_ARIA_VALUE_MAX
import ru.musintimur.instastat.common.constants.ATTR_ARIA_VALUE_MIN
import ru.musintimur.instastat.common.constants.ATTR_ARIA_VALUE_NOW
import ru.musintimur.instastat.common.constants.ATTR_DATA_PROVIDE

fun DIV.dataProvide(value: String) {
    StringAttribute()[this, ATTR_DATA_PROVIDE] = value
}

fun DIV.ariaValueNow(value: Int) {
    StringAttribute()[this, ATTR_ARIA_VALUE_NOW] = value.toString()
}

fun DIV.ariaValueMin(value: Int) {
    StringAttribute()[this, ATTR_ARIA_VALUE_MIN] = value.toString()
}

fun DIV.ariaValueMax(value: Int) {
    StringAttribute()[this, ATTR_ARIA_VALUE_MAX] = value.toString()
}

fun DIV.ariaLabelLedBy(value: String) {
    StringAttribute()[this, "aria-labelledby"] = value
}