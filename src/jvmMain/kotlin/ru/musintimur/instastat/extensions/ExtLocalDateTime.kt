package ru.musintimur.instastat.extensions

import ru.musintimur.instastat.common.constants.PATTERN_LOCAL_DATE_TIME
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getDateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_LOCAL_DATE_TIME)

fun LocalDateTime.toSqlLiteText(): String = this.format(getDateTimeFormatter())