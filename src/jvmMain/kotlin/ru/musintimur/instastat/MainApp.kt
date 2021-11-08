package ru.musintimur.instastat

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.sessions.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.musintimur.instastat.api.addingApi
import ru.musintimur.instastat.api.api
import ru.musintimur.instastat.api.apiAuth
import ru.musintimur.instastat.common.constants.STATIC_FOLDER
import ru.musintimur.instastat.database.InstastatDatabase
import ru.musintimur.instastat.database.getInstastatDatabaseDriver
import ru.musintimur.instastat.model.entities.UserGroups
import ru.musintimur.instastat.repository.InstastatRepository
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.auth.*
import ru.musintimur.instastat.web.links.getStaticIconsFolder
import ru.musintimur.instastat.web.links.getStaticScriptsFolder
import ru.musintimur.instastat.web.links.getStaticStylesFolder
import ru.musintimur.instastat.web.pages.*
import ru.musintimur.instastat.web.styles.styles

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@ExperimentalCoroutinesApi
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

    install(Sessions) {
        cookie<InstastatSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }

    install(XForwardedHeaderSupport)

    install(CORS) {
        header(HttpHeaders.Authorization)
        header(HttpHeaders.XForwardedProto)
        anyHost()
        allowCredentials = true
    }

    install(Authentication) {
        jwt(JWT_SERVICE_ADMIN) {
            verifier(JWTService.verifier)
            validate { call ->
                val payload = call.payload
                val claim = payload.getClaim(CLAIM_USER_NAME)
                val claimString = claim.asString()
                val user = runCatching {
                    repository.users.getUserByName(claimString)
                }.getOrNull()
                    ?.takeIf { it.getGroup() == UserGroups.ADMIN }
                user
            }
        }
        jwt(JWT_SERVICE_OWNER) {
            verifier(JWTService.verifier)
            validate { call ->
                val payload = call.payload
                val claim = payload.getClaim(CLAIM_USER_NAME)
                val claimString = claim.asString()
                val user = runCatching {
                    repository.users.getUserByName(claimString)
                }.getOrNull()
                    ?.takeIf { it.getGroup() in setOf(UserGroups.ADMIN, UserGroups.OWNER) }
                user
            }
        }
        jwt(JWT_SERVICE_USER) {
            verifier(JWTService.verifier)
            validate { call ->
                val payload = call.payload
                val claim = payload.getClaim(CLAIM_USER_NAME)
                val claimString = claim.asString()
                val user = runCatching {
                    repository.users.getUserByName(claimString)
                }.getOrNull()
                    ?.takeIf { it.getGroup() in setOf(UserGroups.ADMIN, UserGroups.OWNER, UserGroups.USER) }
                user
            }
        }
        jwt(JWT_SERVICE_GUEST) {
            verifier(JWTService.verifier)
            validate { call ->
                val payload = call.payload
                val claim = payload.getClaim(CLAIM_USER_NAME)
                val claimString = claim.asString()
                val user = runCatching {
                    repository.users.getUserByName(claimString)
                }.getOrNull()
                    ?.takeIf { it.getGroup() != UserGroups.UNKNOWN }
                user
            }
        }
    }

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
        addingApi(repository)
        apiAuth(repository.users)
    }
}