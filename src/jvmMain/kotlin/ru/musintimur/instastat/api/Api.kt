package ru.musintimur.instastat.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
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
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.parsers.Selenium
import ru.musintimur.instastat.parsers.doAuth
import ru.musintimur.instastat.parsers.doLogout
import ru.musintimur.instastat.parsers.parsePage
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.components.createCharts
import ru.musintimur.instastat.web.components.statTableReport
import java.time.LocalDate
import kotlin.random.Random

private var isParsingStart = false
private const val INSTAGRAM_LINK_PREFIX = "https://www.instagram.com/p/"

@KtorExperimentalLocationsAPI
@Location(API_DAY_REPORT)
data class DayReport(val dateOfReport: String, val sortOrdering: String?)

@KtorExperimentalLocationsAPI
@Location(API_GRAPH_BLOCK)
data class GraphBlock(val profileName: String, val date1: String, val date2: String)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_HISTORY_POSTS)
data class ProfileHistoryPosts(val profileName: String, val date1: String, val date2: String)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_HISTORY_FOLLOWERS)
data class ProfileHistoryFollowers(val profileName: String, val date1: String, val date2: String)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_HISTORY_FOLLOWINGS)
data class ProfileHistoryFollowings(val profileName: String, val date1: String, val date2: String)

@KtorExperimentalLocationsAPI
@Location(API_PROFILE_ACTIVATE)
data class ProfileActivate(val profileId: Long, val isActive: Boolean)

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

    get<GraphBlock> { graphBlock ->
        val statReportFunction: DIV.() -> Unit = {
            createCharts(graphBlock)
        }
        call.respondDiv(statReportFunction, STAT_GRAPH_BLOCK)
    }

    get(API_PARSE_PROGRESS) {
        val progress = db.profiles.getParserProgress()
        call.respond(ParserProgress(progress))
    }

    get<ProfileHistoryPosts> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryPosts(it, profile.date1, profile.date2)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Данные не найдены.")
    }

    get<ProfileHistoryFollowers> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryFollowers(it, profile.date1, profile.date2)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Данные не найдены.")
    }

    get<ProfileHistoryFollowings> { profile ->
        val name = profile.profileName
        db.profiles.getProfileByName(name)?.let {
            val result = db.profilesHistory.getProfileHistoryFollowings(it, profile.date1, profile.date2)
            call.respond(PeriodHistory(result))
        } ?: call.respond(HttpStatusCode.NotFound, "Данные не найдены.")
    }

    post(API_PROFILE_ACTIVATE) {
        val webParameters = call.receiveParameters()
        val profileId = webParameters["profileId"]?.toLongOrNull()
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

    post(API_ADD_POST) {
        val webParameters = call.receiveParameters()
        val postUrl = webParameters[Post::postUrl.name]?.trim()

        when {
            postUrl.isNullOrBlank() ->
                call.respond(HttpStatusCode.NotAcceptable, "Пустая ссылка на пост.")
            postUrl.length != 40
                    || !postUrl.startsWith(INSTAGRAM_LINK_PREFIX)
                    || !postUrl.endsWith('/')
                    || postUrl.substringAfter(INSTAGRAM_LINK_PREFIX).count { it == '/' } != 1 ->
                call.respond(HttpStatusCode.NotAcceptable, "Неправильная ссылка на пост.")
            db.posts.getPostByUrl(postUrl) != null ->
                call.respond(HttpStatusCode.Conflict, "Пост уже добавлен.")
            else -> {
                db.posts.addPost(postUrl)
                call.respond(HttpStatusCode.OK, HttpStatusCode.OK.description)
            }
        }
    }
}