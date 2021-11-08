package ru.musintimur.instastat.extensions

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.locations.get
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import ru.musintimur.instastat.repository.contracts.UsersContract
import ru.musintimur.instastat.web.auth.InstastatSession
import ru.musintimur.instastat.web.auth.signInAsGuest
import java.util.*

@KtorExperimentalLocationsAPI
inline fun <reified T : Any> Route.authGet(
    db: UsersContract,
    noinline block: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
) = get<T> {
        val currentUser = call.sessions.get<InstastatSession>()
        if (currentUser?.token == null ||
            Calendar.getInstance().apply { add(Calendar.HOUR, 1) }
                .after(Date(currentUser.expireDate))
        ) {
            signInAsGuest(db, this).token
        }
        block(this as T)
    }