package ru.musintimur.instastat.web.javascript.canvas

import org.w3c.dom.HTMLCanvasElement
import org.w3c.xhr.XMLHttpRequest
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.common.messages.DayCount
import ru.musintimur.instastat.common.messages.PeriodHistory
import ru.musintimur.instastat.web.javascript.extensions.applyUrlParams
import ru.musintimur.instastat.web.javascript.extensions.collectParameters
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
    val params = canvas.collectParameters()
    val request = XMLHttpRequest()
    request.setupGet(API_PROFILE_HISTORY_POSTS.applyUrlParams(params)) {
        val result = request.responseText
        drawChart(canvas, result, "Posts")
    }.send()
}

fun setupCanvasFollowers(canvas: HTMLCanvasElement) {
    val params = canvas.collectParameters()
    val request = XMLHttpRequest()
    request.setupGet(API_PROFILE_HISTORY_FOLLOWERS.applyUrlParams(params)) {
        val result = request.responseText
        drawChart(canvas, result, "Followers")
    }.send()
}

fun setupCanvasFollowings(canvas: HTMLCanvasElement) {
    val params = canvas.collectParameters()
    val request = XMLHttpRequest()
    request.setupGet(API_PROFILE_HISTORY_FOLLOWINGS.applyUrlParams(params)) {
        val result = request.responseText
        drawChart(canvas, result, "Followings")
    }.send()
}