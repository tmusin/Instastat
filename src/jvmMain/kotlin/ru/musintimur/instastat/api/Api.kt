package ru.musintimur.instastat.api

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.html.DIV
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.asSqlLiteDate
import ru.musintimur.instastat.extensions.log
import ru.musintimur.instastat.extensions.respondDiv
import ru.musintimur.instastat.common.messages.ParserProgress
import ru.musintimur.instastat.common.messages.PeriodHistory
import ru.musintimur.instastat.parsers.Selenium
import ru.musintimur.instastat.parsers.parsePage
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.components.statTableReport
import java.time.LocalDate

private var isParsingStart = false

@KtorExperimentalLocationsAPI
@Location(API_DAY_REPORT)
data class DayReport(val dateOfReport: String)

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
        "Start parsing...".log()
        isParsingStart = true

        runCatching {
            val profiles = withContext(Dispatchers.IO) {
                db.profiles.getAllActiveProfilesForUpdate()
            }
            Selenium.initSelenium()
            delay(3000)
            profiles.forEach { profile ->
                parsePage(db, profile)
                delay(10000)
            }
            Selenium.closeSelenium()
        }.onFailure {
            "Error: ${it.message}".log(call)
        }
        isParsingStart = false
        call.respondText { "OK" }
    }

    get<DayReport> { dayReport ->
        val date = dayReport.dateOfReport.asSqlLiteDate() ?: LocalDate.now()
        val report = db.profilesHistory.getPostsHistory(date)
        val statReportFunction: DIV.() -> Unit = {
            statTableReport(report)
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
        } ?: call.respond(HttpStatusCode.NotFound, "Data not found")
    }

    get<ProfileHistoryFollowers> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryFollowers(it)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Data not found")
    }

    get<ProfileHistoryFollowings> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryFollowings(it)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Data not found")
    }
}