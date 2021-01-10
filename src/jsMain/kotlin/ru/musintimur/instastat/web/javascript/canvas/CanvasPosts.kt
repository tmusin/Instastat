package ru.musintimur.instastat.web.javascript.canvas

import org.w3c.dom.HTMLCanvasElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.API_PROFILE_HISTORY_FOLLOWERS
import ru.musintimur.instastat.common.constants.API_PROFILE_HISTORY_FOLLOWINGS
import ru.musintimur.instastat.common.constants.API_PROFILE_HISTORY_POSTS
import ru.musintimur.instastat.common.constants.ATTR_PROFILE_NAME
import ru.musintimur.instastat.common.messages.DayCount
import ru.musintimur.instastat.common.messages.PeriodHistory
import ru.musintimur.instastat.web.javascript.extensions.setupGet
import ru.musintimur.instastat.web.javascript.external.*

private fun drawChart(canvas: HTMLCanvasElement, json: String, name: String) {
    val history = JSON.parse<PeriodHistory>(json).entries.unsafeCast<Array<DayCount>>()

    val arrLabels = mutableListOf<String>()
    val arrData = mutableListOf<Long>()
    for (i in history.indices) {
        val value = history[i]
        arrLabels.add(value.date)
        arrData.add(value.count)
    }

    val config = getChartConfig(arrLabels.toTypedArray(), arrData.toTypedArray(), name)

    Chart(canvas, config)
}

fun setupCanvasPosts(canvas: HTMLCanvasElement) {
    val profileName = canvas.getAttribute(ATTR_PROFILE_NAME)
    val request = XMLHttpRequest()
    request.setupGet("$API_PROFILE_HISTORY_POSTS/$profileName") {
        val result = request.responseText
        drawChart(canvas, result, "Posts")
    }.send()
}

fun setupCanvasFollowers(canvas: HTMLCanvasElement) {
    val profileName = canvas.getAttribute(ATTR_PROFILE_NAME)
    val request = XMLHttpRequest()
    request.setupGet("$API_PROFILE_HISTORY_FOLLOWERS/$profileName") {
        val result = request.responseText
        drawChart(canvas, result, "Followers")
    }.send()
}

fun setupCanvasFollowings(canvas: HTMLCanvasElement) {
    val profileName = canvas.getAttribute(ATTR_PROFILE_NAME)
    val request = XMLHttpRequest()
    request.setupGet("$API_PROFILE_HISTORY_FOLLOWINGS/$profileName") {
        val result = request.responseText
        drawChart(canvas, result, "Followings")
    }.send()
}