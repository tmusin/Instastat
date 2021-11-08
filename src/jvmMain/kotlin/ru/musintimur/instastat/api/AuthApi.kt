package ru.musintimur.instastat.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.musintimur.instastat.common.constants.AUTH_API
import ru.musintimur.instastat.common.constants.SIGN_OUT
import ru.musintimur.instastat.common.constants.USER_GROUP_API
import ru.musintimur.instastat.model.entities.UserGroups
import ru.musintimur.instastat.repository.contracts.UsersContract
import ru.musintimur.instastat.web.auth.changeUser
import ru.musintimur.instastat.web.auth.hash
import ru.musintimur.instastat.web.auth.logOut
import ru.musintimur.instastat.web.pages.MAIN_PAGE

@KtorExperimentalLocationsAPI
@Location(USER_GROUP_API)
data class ApiUserGroup(val username: String)

@KtorExperimentalLocationsAPI
@Location("/hash")
data class ApiPasswordHash(val password: String)

@KtorExperimentalLocationsAPI
@Location(SIGN_OUT)
class SignOut

@KtorExperimentalLocationsAPI
fun Route.apiAuth(db: UsersContract) {
    post(AUTH_API) {
        val params = call.receiveParameters()
        val userName = params["username"]?.trim()
        val password = params["password"]?.trim()
        if (userName.isNullOrBlank() || password.isNullOrBlank()) {
            call.respond(HttpStatusCode.Forbidden, "Пустой логин или пароль.")
        } else {
            runCatching {
                changeUser(db, this, userName, password)
            }.onSuccess {
                call.respond(HttpStatusCode.OK, "Успешно.")
            }.onFailure {
                call.respond(HttpStatusCode.Forbidden, it.message ?: "Ошибка.")
            }
        }
    }

    post<SignOut> {
        logOut(db, this)
        call.respondRedirect(MAIN_PAGE)
    }

    get<ApiUserGroup> {
        val user = db.getUserByName(it.username)
        if (user != null) {
            call.respond { user.getGroup() }
        } else {
            call.respond { UserGroups.UNKNOWN }
        }
    }

    get<ApiPasswordHash> { params ->
        if (params.password.isNotBlank()) {
            call.respond(hash(params.password))
        } else {
            call.respond("Empty password.")
        }
    }
}