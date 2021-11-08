package ru.musintimur.instastat.web.auth

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import ru.musintimur.instastat.model.entities.User
import ru.musintimur.instastat.model.entities.UserGroups
import ru.musintimur.instastat.repository.contracts.UsersContract
import java.util.*

private val guestLogin: String = System.getenv("API_GUEST_LOGIN")
private val guestPassword: String = System.getenv("API_GUEST_PASSWORD")

fun readUserGroupHash(context: PipelineContext<*, ApplicationCall>): String =
    context.call.sessions.get<InstastatSession>()?.userGroupHash ?: hash(UserGroups.UNKNOWN.name)

@KtorExperimentalLocationsAPI
suspend fun changeUser(
    db: UsersContract,
    context: PipelineContext<*, ApplicationCall>,
    username: String,
    password: String
): User {
    if (username.isBlank() || password.isBlank()) {
        throw InvalidCredentialsException()
    }
    val passwordHash = hash(password)
    val user = db.checkCredentials(username, passwordHash)
        ?: throw InvalidCredentialsException()
    val token = JWTService.generateToken(user)
    val encryptedUserGroup = hash(user.getGroup().toString())
    val endDate = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }.timeInMillis
    context.call.sessions.set(InstastatSession(user.userName, encryptedUserGroup, token, endDate))
    return user
}

@KtorExperimentalLocationsAPI
suspend fun logOut(db: UsersContract, context: PipelineContext<*, ApplicationCall>): User =
    changeUser(db, context, guestLogin, guestPassword)


@KtorExperimentalLocationsAPI
suspend fun signInAsGuest(db: UsersContract, context: PipelineContext<*, ApplicationCall>): User =
    logOut(db, context)