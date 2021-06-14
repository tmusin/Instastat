package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.HTMLCanvasElement
import ru.musintimur.instastat.common.constants.*

fun HTMLCanvasElement.collectParameters(): String {
    var params = ""
    if (this.hasAttribute(ATTR_PROFILE_NAME)) {
        params += "profileName=${this.getAttribute(ATTR_PROFILE_NAME)}&"
    }
    if (this.hasAttribute(ATTR_PROFILE_DATE1)) {
        params += "date1=${this.getAttribute(ATTR_PROFILE_DATE1)}&"
    }
    if (this.hasAttribute(ATTR_PROFILE_DATE2)) {
        params += "date2=${this.getAttribute(ATTR_PROFILE_DATE2)}&"
    }
    return params.dropLast(1);
}