package ru.musintimur.instastat

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.musintimur.instastat.api.api
import ru.musintimur.instastat.common.constants.STATIC_FOLDER
import ru.musintimur.instastat.database.InstastatDatabase
import ru.musintimur.instastat.database.getInstastatDatabaseDriver
import ru.musintimur.instastat.repository.InstastatRepository
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.links.getStaticIconsFolder
import ru.musintimur.instastat.web.links.getStaticScriptsFolder
import ru.musintimur.instastat.web.links.getStaticStylesFolder
import ru.musintimur.instastat.web.pages.*
import ru.musintimur.instastat.web.styles.styles

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@ExperimentalCoroutinesApi
@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    val db = InstastatDatabase(getInstastatDatabaseDriver())
    val repository: Repository = InstastatRepository(db)

    install(Locations) {
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install(CallLogging)

    routing {
        static(STATIC_FOLDER) {
            files(getStaticScriptsFolder())
            files(getStaticIconsFolder())
            files(getStaticStylesFolder())
        }
        styles()
        mainPage(repository)
        dictionaryPage(repository)
        postsPage(repository)
        profiles()
        comments(repository)
        api(repository)
    }
}