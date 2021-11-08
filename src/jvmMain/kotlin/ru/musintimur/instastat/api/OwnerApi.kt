package ru.musintimur.instastat.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.delay
import ru.musintimur.instastat.common.constants.API_ADD_PROFILE
import ru.musintimur.instastat.common.constants.API_PARSE_PROGRESS
import ru.musintimur.instastat.common.constants.API_PROFILE_ACTIVATE
import ru.musintimur.instastat.common.constants.API_START_PARSING
import ru.musintimur.instastat.common.messages.ParserProgress
import ru.musintimur.instastat.extensions.authGet
import ru.musintimur.instastat.extensions.log
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.parsers.Selenium
import ru.musintimur.instastat.parsers.doAuth
import ru.musintimur.instastat.parsers.doLogout
import ru.musintimur.instastat.parsers.parsePage
import ru.musintimur.instastat.repository.Repository
import kotlin.random.Random

var isParsingStart = false

@KtorExperimentalLocationsAPI
@Location(API_PARSE_PROGRESS)
class ApiParseProgress

@KtorExperimentalLocationsAPI
fun Route.addingApi(db: Repository) {
    post(API_START_PARSING) {
        if (isParsingStart) return@post
        "Начинаем проверку аккаунтов...".log()
        isParsingStart = true

        runCatching {
            val profiles = db.profiles.getAllActiveProfilesForUpdate()
            if (profiles.isNotEmpty()) {
                Selenium.initSelenium()
                delay(3000)
                doAuth()
                delay(3000)
                profiles.forEach { profile ->
                    parsePage(db, profile)
                    delay(Random.nextInt(5, 13) * 1000L)
                }
                delay(3000)
                doLogout()
                delay(3000)
                Selenium.closeSelenium()
                "Обработка окончена.".log()
            } else {
                "На сегодня вся информация уже собрана.".log()
            }
        }.onFailure {
            isParsingStart = false
            it.message?.log()
        }.onSuccess {
            isParsingStart = false
            call.respondText { "OK" }
        }
    }

    get<ApiParseProgress> {
        val progress = db.profiles.getParserProgress()
        call.respond(ParserProgress(progress))
    }

    post(API_PROFILE_ACTIVATE) {
        val webParameters = call.receiveParameters()
        val profileId = webParameters["profileId"]?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid profile ID.")
        val active: Boolean = webParameters["isActive"]?.toBoolean() == true
        db.profiles.setProfileActivity(profileId, active)
        call.respond("OK")
    }

    post(API_ADD_PROFILE) {
        val webParameters = call.receiveParameters()
        val profileName = webParameters[Profile::profileName.name]?.lowercase()?.trim()

        when {
            profileName.isNullOrBlank() ->
                call.respond(HttpStatusCode.NotAcceptable, "Пустое имя профиля.")
            db.profiles.getProfileByName(profileName.lowercase().trim()) != null ->
                call.respond(HttpStatusCode.Conflict, "Профиль уже добавлен.")
            else -> {
                db.profiles.addProfile(profileName)
                call.respond(HttpStatusCode.OK, HttpStatusCode.OK.description)
            }
        }
    }
}