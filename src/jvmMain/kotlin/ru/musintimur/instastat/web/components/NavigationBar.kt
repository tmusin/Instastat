package ru.musintimur.instastat.web.components

import io.ktor.util.*
import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.dataTarget
import ru.musintimur.instastat.extensions.dataToggle
import ru.musintimur.instastat.web.pages.MAIN_PAGE
import ru.musintimur.instastat.web.pages.SITE_NAME

@KtorExperimentalAPI
fun BODY.navigationBar(nmId: String, menu: Map<String, String>) {
    div {
        classes = setOf(CLS_BOOTSTRAP_CONTAINER, CLS_BOOTSTRAP_NAV_CONT)
        nav {
            id = nmId
            classes = setOf(
                CLS_BOOTSTRAP_NAV_BAR,
                CLS_BOOTSTRAP_FIXED_TOP,
                CLS_BOOTSTRAP_NAV_BAR_LIGHT,
                CLS_BOOTSTRAP_BG_LIGHT,
                CLS_BOOTSTRAP_NAV_BAR_EXPAND_SM
            )
            a {
                classes = setOf(CLS_BOOTSTRAP_NAV_BAR_BRAND)
                href = MAIN_PAGE
                +SITE_NAME
            }
            div {
                classes = setOf(
                    CLS_BOOTSTRAP_COLLAPSE,
                    CLS_BOOTSTRAP_NAV_BAR_COLLAPSE,
                    CLS_BOOTSTRAP_JUSTIFY_CONTENT_CENTER
                )
                id = NAVBAR_SUPPORTED_CONTENT
                ul {
                    classes = setOf(CLS_BOOTSTRAP_NAV, CLS_BOOTSTRAP_NAV_BAR_NAV)
                    menu.forEach {
                        li {
                            classes = setOf(CLS_BOOTSTRAP_NAV_ITEM)
                            dataToggle("collapse")
                            dataTarget(".navbar-collapse.show")
                            a {
                                classes = setOf(CLS_BOOTSTRAP_NAV_LINK)
                                href = it.value

                                +it.key
                            }
                        }
                    }
                }
            }
        }
    }
}