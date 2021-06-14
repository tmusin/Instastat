package ru.musintimur.instastat.web.components

import io.ktor.locations.*
import kotlinx.html.*
import ru.musintimur.instastat.api.GraphBlock
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.asSqlLiteDate
import ru.musintimur.instastat.extensions.profileName
import java.time.LocalDate

fun DIV.profileChart(
    id_: String,
    profileName: String,
    date1: LocalDate,
    date2: LocalDate
) {
    div {
        classes = setOf(CLS_CANVAS_SECTION, CLS_BOOTSTRAP_CONTAINER)
        canvas {
            id = id_
            width = "640"
            height = "480"
            profileName(profileName, date1, date2)
        }
    }
}

@KtorExperimentalLocationsAPI
fun DIV.createCharts(graphBlock: GraphBlock) {
    val profileName = graphBlock.profileName
    val date1 = graphBlock.date1.asSqlLiteDate() ?: LocalDate.now().plusMonths(1L)
    val date2 = graphBlock.date2.asSqlLiteDate() ?: LocalDate.now()
    profileChart(CANVAS_PROFILE_CHART_POSTS, profileName, date1, date2)
    profileChart(CANVAS_PROFILE_CHART_FOLLOWERS, profileName, date1, date2)
    profileChart(CANVAS_PROFILE_CHART_FOLLOWINGS, profileName, date1, date2)
}