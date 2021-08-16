package ru.musintimur.instastat.web.pages

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.*
import ru.musintimur.instastat.extensions.datePickerScript
import ru.musintimur.instastat.extensions.log
import ru.musintimur.instastat.extensions.toSqlLiteText
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.components.pageTemplate
import java.time.LocalDate

const val SITE_NAME = "Instastat"

const val MAIN_PAGE = "/"
const val MAIN_PAGE_NAME = "Главная"

const val DICTIONARY_PAGE = "/accounts"
const val DICTIONARY_PAGE_NAME = "Аккаунты"

const val COMMENTS_PAGE = "/comments"
const val COMMENTS_PAGE_NAME = "Комментарии"

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.mainPage(db: Repository) {

    get(MAIN_PAGE) {
        val reportDate = db.profilesHistory.getLastMeasure() ?: LocalDate.now()
        reportDate.toSqlLiteText().log()
        val report = db.profilesHistory.getPostsHistory(reportDate)
        val todayProgress = db.profiles.getParserProgress()
        todayProgress.toString().log()

        call.respondHtml {
            pageTemplate(MAIN_PAGE_NAME, { datePickerScript() } ) {
                statTable(reportDate, report, todayProgress)
            }
        }
    }
}