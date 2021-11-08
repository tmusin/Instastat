package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*

fun BODY.authBlock() {
    div {
        classes = setOf(CLS_CONTAINER_BOOTSTRAP, CLS_AUTHENTICATE_SECTION)
        div {
            classes = setOf("row")
            div {
                classes = setOf("col-md-4", "col-md-offset-4")
                div {
                    classes = setOf("panel", "panel-default")
                    div {
                        classes = setOf("panel-heading")
                        h3 {
                            classes = setOf("panel-title")
                            +"Вход"
                        }
                    }
                    div {
                        classes = setOf("panel-body")
                        form {
                            method = FormMethod.post
                            id = FORM_ID_AUTHENTICATE
                            p {
                                classes = setOf(PARAGRAPH_ERROR)
                                id = "login-error"
                            }
                            div {
                                classes = setOf("form-group")
                                label {
                                    this.htmlFor = "username"
                                    +"Логин"
                                }
                                textInput {
                                    name = "username"
                                    classes = setOf("form-control")
                                    id = "username"
                                    placeholder = "Логин"
                                }
                            }
                            div {
                                classes = setOf("form-group")
                                label {
                                    this.htmlFor = "password"
                                    +"Пароль"
                                }
                                passwordInput {
                                    name = "password"
                                    classes = setOf("form-control")
                                    id = "password"
                                    placeholder = "Пароль"
                                }
                            }
                            submitInput {
                                classes = setOf(BUTTON_BOOTSTRAP, BUTTON_BOOTSTRAP_PRIMARY)
                                value = "Войти"
                            }
                        }
                    }
                }
            }
        }
    }
}