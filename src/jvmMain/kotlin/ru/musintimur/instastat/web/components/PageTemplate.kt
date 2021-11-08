package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.NAVIGATION_BAR_MAIN
import ru.musintimur.instastat.extensions.*
import ru.musintimur.instastat.model.entities.UserGroups
import ru.musintimur.instastat.model.entities.UserGroups.Companion.signedInUserGroups
import ru.musintimur.instastat.web.auth.hash
import ru.musintimur.instastat.web.pages.*

data class MainMenuEntry(
    val entryCaption: String,
    val href: String,
    val predicate: (String) -> Boolean = { true }
)

val mainSections: List<MainMenuEntry> = listOf(
    MainMenuEntry(MAIN_PAGE_NAME, MAIN_PAGE),
    MainMenuEntry(DICTIONARY_PAGE_NAME, DICTIONARY_PAGE
    ) { hash ->
        hash in setOf(
            hash(UserGroups.ADMIN.toString()),
            hash(UserGroups.OWNER.toString()))
    },
    MainMenuEntry(POSTS_PAGE_NAME, POSTS_PAGE),
)

fun HTML.pageTemplate(
    pageName: String,
    userGroupHash: String,
    additionalScripts: (BODY.() -> Unit)? = null,
    pageContent: BODY.() -> Unit)
{
    head {
        title("$SITE_NAME | $pageName")

        defMeta()
        googleFonts()
        bootstrapCss()
        customCss()
    }
    body {
        if (userGroupHash in signedInUserGroups) {
            navigationBar(NAVIGATION_BAR_MAIN, mainSections, userGroupHash)
            pageContent()
        } else {
            authBlock()
        }
        staticScripts()
        bootstrapJs()
        additionalScripts?.invoke(this)
    }
}