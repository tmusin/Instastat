package ru.musintimur.instastat.extensions

import kotlinx.html.CANVAS
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.ATTR_PROFILE_DATE1
import ru.musintimur.instastat.common.constants.ATTR_PROFILE_DATE2
import ru.musintimur.instastat.common.constants.ATTR_PROFILE_NAME
import java.time.LocalDate

fun CANVAS.profileName(name: String, date1: LocalDate, date2: LocalDate) {
    StringAttribute()[this, ATTR_PROFILE_NAME] = name
    StringAttribute()[this, ATTR_PROFILE_DATE1] = date1.toSqlLiteText()
    StringAttribute()[this, ATTR_PROFILE_DATE2] = date2.toSqlLiteText()
}