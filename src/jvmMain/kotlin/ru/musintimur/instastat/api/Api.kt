package ru.musintimur.instastat.api

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get
import kotlinx.coroutines.delay
import kotlinx.html.DIV
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.asSqlLiteDate
import ru.musintimur.instastat.extensions.log
import ru.musintimur.instastat.extensions.respondDiv
import ru.musintimur.instastat.common.messages.ParserProgress
import ru.musintimur.instastat.common.messages.PeriodHistory
import ru.musintimur.instastat.parsers.Selenium
import ru.musintimur.instastat.parsers.doAuth
import ru.musintimur.instastat.parsers.doLogout
import ru.musintimur.instastat.parsers.parsePage
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.components.statTableReport
import java.time.LocalDate
import kotlin.random.Random

private var isParsingStart = false

@KtorExperimentalLocationsAPI
@Location(API_DAY_REPORT)
data class DayReport(val dateOfReport: String, val sortOrdering: String?)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_HISTORY_POSTS_PARAMETER)
data class ProfileHistoryPosts(val profileName: String)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_HISTORY_FOLLOWERS_PARAMETER)
data class ProfileHistoryFollowers(val profileName: String)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_HISTORY_FOLLOWINGS_PARAMETER)
data class ProfileHistoryFollowings(val profileName: String)

@KtorExperimentalLocationsAPI
fun Route.api(db: Repository) {
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
        }
        isParsingStart = false
        call.respondText { "OK" }
    }

    get<DayReport> { dayReport ->
        val date = dayReport.dateOfReport.asSqlLiteDate() ?: LocalDate.now()
        val sortOrdering = dayReport.sortOrdering ?: DROPDOWN_SORT_TYPE_AZ
        val report = db.profilesHistory.getPostsHistory(date)
        val statReportFunction: DIV.() -> Unit = {
            statTableReport(report, sortOrdering)
        }
        call.respondDiv(statReportFunction, STAT_REPORT_BLOCK)
    }

    get(API_PARSE_PROGRESS) {
        val progress = db.profiles.getParserProgress()
        call.respond(ParserProgress(progress))
    }

    get<ProfileHistoryPosts> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryPosts(it)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Данные не найдены.")
    }

    get<ProfileHistoryFollowers> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryFollowers(it)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Данные не найдены.")
    }

    get<ProfileHistoryFollowings> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryFollowings(it)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Данные не найдены.")
    }
}