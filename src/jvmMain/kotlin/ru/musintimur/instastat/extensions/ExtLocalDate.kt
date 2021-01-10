package ru.musintimur.instastat.extensions

import ru.musintimur.instastat.common.constants.PATTERN_LOCAL_DATE
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getDateFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_LOCAL_DATE)

fun LocalDate.toSqlLiteText(): String = this.format(getDateFormatter())