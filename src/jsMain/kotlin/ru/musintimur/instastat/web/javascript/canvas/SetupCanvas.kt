package ru.musintimur.instastat.web.javascript.canvas

import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.get
import ru.musintimur.instastat.common.constants.CANVAS_PROFILE_CHART_FOLLOWERS
import ru.musintimur.instastat.common.constants.CANVAS_PROFILE_CHART_FOLLOWINGS
import ru.musintimur.instastat.common.constants.CANVAS_PROFILE_CHART_POSTS

fun setupCanvas() {
    val canvases = document.getElementsByTagName("canvas")
    for (i in 0 until canvases.length) {
        canvases[i]?.let { item ->
            val canvas = item as HTMLCanvasElement
            when (canvas.id) {
                CANVAS_PROFILE_CHART_POSTS -> {
                    setupCanvasPosts(canvas)
                }
                CANVAS_PROFILE_CHART_FOLLOWERS -> {
                    setupCanvasFollowers(canvas)
                }
                CANVAS_PROFILE_CHART_FOLLOWINGS -> {
                    setupCanvasFollowings(canvas)
                }
                else -> {
                    println("No charts")
                }
            }
        }
    }
}