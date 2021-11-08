package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.dataTarget
import ru.musintimur.instastat.extensions.dataToggle
import ru.musintimur.instastat.model.entities.UserGroups.Companion.signedInUserGroups
import ru.musintimur.instastat.web.pages.MAIN_PAGE
import ru.musintimur.instastat.web.pages.SITE_NAME

fun BODY.navigationBar(nmId: String, menu: List<MainMenuEntry>, userGroupHash: String) {
    val isSignedIn = userGroupHash in signedInUserGroups
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
                    menu.filter { it.predicate(userGroupHash) }
                        .forEach {
                            li {
                                classes = setOf(CLS_BOOTSTRAP_NAV_ITEM)
                                dataToggle("collapse")
                                dataTarget(".navbar-collapse.show")
                                a {
                                    classes = setOf(CLS_BOOTSTRAP_NAV_LINK)
                                    href = it.href

                                    +it.entryCaption
                                }
                            }
                        }
                }
            }
            if (isSignedIn) {
                form {
                    classes = setOf("form-inline", "my-2", "my-lg-0")
                    action = SIGN_OUT
                    method = FormMethod.post
                    div {
                        classes = setOf("input-group")
                        span {
                            classes = setOf("input-group-btn", "ml-2")
                            button {
                                classes = setOf(BUTTON_BOOTSTRAP, BUTTON_BOOTSTRAP_SECONDARY)
                                type = ButtonType.submit
                                span {
                                    classes = setOf("fa", "fa-sign-out")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}