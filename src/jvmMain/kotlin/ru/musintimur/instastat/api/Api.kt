package ru.musintimur.instastat.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.html.DIV
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.common.messages.PeriodHistory
import ru.musintimur.instastat.extensions.*
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.parsers.*
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.components.createCharts
import ru.musintimur.instastat.web.components.statTableReport
import java.io.FileOutputStream
import java.nio.file.*
import java.time.LocalDate
import java.time.LocalDateTime

private const val INSTAGRAM_LINK_PREFIX = "https://www.instagram.com/p/"

private val printPath: String = runCatching {
    System.getenv("COMMENTS_REPORT_PATH")
}.getOrDefault("reports")

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
fun Route.api(db: Repository) {
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

    post(API_ADD_POST) {
        val webParameters = call.receiveParameters()
        val postUrl = webParameters[Post::postUrl.name]?.trim()

        when {
            postUrl.isNullOrBlank() ->
                call.respond(HttpStatusCode.NotAcceptable, "Пустая ссылка на пост.")
            !postUrl.startsWith(INSTAGRAM_LINK_PREFIX)
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

    post(API_PARSE_POST) {
        if (isParsingStart) {
            "Дождитесь окончания другого процесса".log()
            return@post
        }
        isParsingStart = true
        val webParameters = call.receiveParameters()
        val postUrl = webParameters[Post::postUrl.name]?.trim()
        if (postUrl == null) {
            "Пустой параметр ссылки на пост.".log()
            return@post
        }
        val post = db.posts.getPostByUrl(postUrl)
        if (post == null) {
            "Поста нет в БД".log()
            return@post
        }

        runCatching {
            Selenium.initSelenium()
            delay(3000)
            doAuth()
            delay(3000)
            parsePost(db, post)
            delay(3000)
            doLogout()
            delay(3000)
            Selenium.closeSelenium()
            "Обработка окончена.".log()
        }
        isParsingStart = false
        call.respondText { "OK" }
    }

    post(API_PRINT_COMMENTS) {
        val webParameters = call.receiveParameters()
        val postId = webParameters[Post::postId.name]?.trim()?.toIntOrNull()
        if (postId == null) {
            "Неверный ID поста".log()
            return@post
        }
        val post = db.posts.getPostById(postId)
        "Печать комментариев поста ${post?.postUrl}".log()
        val comments = post?.let { db.comments.getPostComments(it) } ?: emptyList()
        val workbook = HSSFWorkbook()
        val id = post?.postUrl?.subSequence(28, 39)?.toString() ?: ""
        val sheet = workbook.createSheet(id)
        comments.forEachIndexed { index, comment ->
            val row = sheet.createRow(index)
            val cellAuthor = row.createCell(0)
            cellAuthor.setCellValue(comment.author)
            val cellComment = row.createCell(1)
            cellComment.setCellValue(comment.text)
        }
        sheet.autoSizeColumn(0)
        sheet.autoSizeColumn(1)
        val fileName = "${LocalDateTime.now().toSqlLiteText().replace(' ', '_')}_$id.xls"
        fileName.log()
        withContext(Dispatchers.IO) {
            runCatching {
                val createdFile = Paths.get(printPath, fileName).toFile()
                FileOutputStream(createdFile).use { workbook.write(it) }
            }.onSuccess {
                "Напечатано".log()
            }.onFailure {
                "Ошибка при печати файла:\n${it.message}".log()
            }
        }
    }
}