package ru.musintimur.instastat.web.pages

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.padZeros
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.auth.readUserGroupHash
import ru.musintimur.instastat.web.components.pageTemplate

const val DICTIONARY_PAGE_ADD = "$DICTIONARY_PAGE/add"

fun Route.dictionaryPage(db: Repository) {
    get(DICTIONARY_PAGE) {
        val accounts = db.profiles.getAllProfiles().sortedBy { it.profileName }
        call.respondHtml {
            pageTemplate(DICTIONARY_PAGE_NAME, readUserGroupHash(this@get)) {
                div {
                    classes = setOf(CLS_ACCOUNTS_SECTION)
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_4)
                        buttonInput {
                            classes = setOf(CLS_INPUT, BUTTON_BOOTSTRAP_PRIMARY)
                            id = INPUT_ID_BUTTON_ADD_PROFILE
                            value = "Добавить"
                        }
                    }
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_2, CLS_MT_2)
                        div {
                            classes = setOf(CLS_COL_MD_AUTO)
                            accounts.forEachIndexed { index, profile ->
                                div {
                                    checkBoxInput {
                                        classes = setOf(CLS_INPUT, CLS_INPUT_DICTIONARY_ENTRY)
                                        checked = profile.isActive
                                        value = profile.profileId.toString()
                                        label {
                                            classes = setOf(CLS_ML_2)
                                            +"${index.inc().padZeros(2)}. ${profile.profileName}"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    get(DICTIONARY_PAGE_ADD) {
        call.respondHtml {
            pageTemplate(DICTIONARY_PAGE_NAME, readUserGroupHash(this@get)) {
                div {
                    classes = setOf(CLS_ACCOUNTS_SECTION)
                    form {
                        classes = setOf(CLS_ROW, CLS_ML_4)
                        id = FORM_ID_ADD_PROFILE
                        input {
                            name = Profile::profileName.name
                        }
                        submitInput {
                            classes = setOf(CLS_ML_2, BUTTON_BOOTSTRAP_PRIMARY)
                            value = "Сохранить"
                        }
                    }
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_4)
                        id = ADD_PROFILE_MESSAGE
                    }
                }
            }
        }
    }
}