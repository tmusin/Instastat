package ru.musintimur.instastat.web.components

import io.ktor.util.*
import kotlinx.html.*
import ru.musintimur.instastat.common.constants.NAVIGATION_BAR_MAIN
import ru.musintimur.instastat.extensions.*
import ru.musintimur.instastat.web.pages.*

val mainSections: Map<String, String> = mapOf(
    MAIN_PAGE_NAME to MAIN_PAGE,
    DICTIONARY_PAGE_NAME to DICTIONARY_PAGE,
    COMMENTS_PAGE_NAME to COMMENTS_PAGE
)

val emptyMenu: Map<String, String> = emptyMap

@KtorExperimentalAPI
fun HTML.pageTemplate(pageName: String, additionalScripts: (BODY.() -> Unit)? = null, pageContent: BODY.() -> Unit) {
    head {
        title("$SITE_NAME | $pageName")

        defMeta()
        googleFonts()
        bootstrapCss()
        customCss()
    }
    body {
        navigationBar(NAVIGATION_BAR_MAIN, mainSections)
        pageContent()
        staticScripts()
        bootstrapJs()
        additionalScripts?.invoke(this)
    }
}