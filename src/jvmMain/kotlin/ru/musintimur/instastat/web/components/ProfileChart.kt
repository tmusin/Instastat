package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.CLS_BOOTSTRAP_CONTAINER
import ru.musintimur.instastat.common.constants.CLS_CANVAS_SECTION
import ru.musintimur.instastat.extensions.profileName

fun BODY.profileChart(id_: String, profileName: String) {
    div {
        classes = setOf(CLS_CANVAS_SECTION, CLS_BOOTSTRAP_CONTAINER)
        canvas {
            id = id_
            width = "640"
            height = "480"
            profileName(profileName)
        }
    }
}