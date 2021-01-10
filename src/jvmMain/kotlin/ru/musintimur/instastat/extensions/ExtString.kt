package ru.musintimur.instastat.extensions

import io.ktor.application.*
import ru.musintimur.instastat.common.constants.ENV_ERROR_LOGGING
import ru.musintimur.instastat.common.constants.ENV_LOGGING
import java.time.LocalDate
import java.time.LocalDateTime

private val isLoggerOn: Boolean = runCatching {
    System.getenv(ENV_LOGGING).toBoolean()
}.getOrDefault(false)

private val isErrorLoggerOn: Boolean = runCatching {
    System.getenv(ENV_ERROR_LOGGING).toBoolean()
}.getOrDefault(false)

fun String.log(call: ApplicationCall? = null) {
    if (isLoggerOn) call?.application?.environment?.log?.info(this) ?: println(this)
}
fun String.errorLog(call: ApplicationCall? = null) {
    if (isErrorLoggerOn) call?.application?.environment?.log?.info(this) ?: println(this)
}

fun String?.asSqlLiteDateTime(): LocalDateTime? =
    runCatching {
        LocalDateTime.parse(this, getDateTimeFormatter())
    }.getOrNull()

fun String?.asSqlLiteDate(): LocalDate? =
    runCatching {
        LocalDate.parse(this, getDateFormatter())
    }.getOrNull()