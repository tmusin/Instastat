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
    val loggerString = "${LocalDateTime.now().toSqlLiteText()}: $this"
    if (isLoggerOn) call?.application?.environment?.log?.info(loggerString) ?: println(loggerString)
}
fun String.errorLog(call: ApplicationCall? = null) {
    val loggerString = "${LocalDateTime.now().toSqlLiteText()}: $this"
    if (isErrorLoggerOn) call?.application?.environment?.log?.info(loggerString) ?: println(loggerString)
}

fun String?.asSqlLiteDateTime(): LocalDateTime? =
    runCatching {
        LocalDateTime.parse(this, getDateTimeFormatter())
    }.getOrNull()

fun String?.asSqlLiteDate(): LocalDate? =
    runCatching {
        LocalDate.parse(this, getDateFormatter())
    }.getOrNull()