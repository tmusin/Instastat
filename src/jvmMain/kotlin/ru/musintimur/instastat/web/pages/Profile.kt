package ru.musintimur.instastat.web.pages

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.*
import ru.musintimur.instastat.common.constants.CANVAS_PROFILE_CHART_FOLLOWERS
import ru.musintimur.instastat.common.constants.CANVAS_PROFILE_CHART_FOLLOWINGS
import ru.musintimur.instastat.common.constants.CANVAS_PROFILE_CHART_POSTS
import ru.musintimur.instastat.web.components.pageTemplate
import ru.musintimur.instastat.web.components.profileChart

const val PROFILE_PAGE = "/profiles"
const val PROFILE_PAGE_PARAMETER = "$PROFILE_PAGE/{profileName}"

@KtorExperimentalLocationsAPI
@Location(PROFILE_PAGE_PARAMETER)
data class ProfilePage(val profileName: String)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.profiles() {
    get<ProfilePage> { page ->
        call.respondHtml {
            pageTemplate(page.profileName) {
                profileChart(CANVAS_PROFILE_CHART_POSTS, page.profileName)
                profileChart(CANVAS_PROFILE_CHART_FOLLOWERS, page.profileName)
                profileChart(CANVAS_PROFILE_CHART_FOLLOWINGS, page.profileName)
            }
        }
    }
}