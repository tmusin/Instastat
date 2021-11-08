package ru.musintimur.instastat.web.pages

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.routing.*
import kotlinx.html.*
import ru.musintimur.instastat.api.GraphBlock
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.dataDateFormat
import ru.musintimur.instastat.extensions.dataProvide
import ru.musintimur.instastat.extensions.datePickerScript
import ru.musintimur.instastat.extensions.toSqlLiteText
import ru.musintimur.instastat.web.auth.readUserGroupHash
import ru.musintimur.instastat.web.components.createCharts
import ru.musintimur.instastat.web.components.pageTemplate
import java.time.LocalDate

const val PROFILE_PAGE = "/profiles"
const val PROFILE_PAGE_PARAMETER = "$PROFILE_PAGE/{profileName}"

@KtorExperimentalLocationsAPI
@Location(PROFILE_PAGE_PARAMETER)
data class ProfilePage(val profileName: String)

@KtorExperimentalLocationsAPI
fun Route.profiles() {
    get<ProfilePage> { page ->
        val date2 = LocalDate.now().toSqlLiteText()
        val date1 = LocalDate.now().minusMonths(1L).toSqlLiteText()
        call.respondHtml {
            pageTemplate(page.profileName, readUserGroupHash(this@get), { datePickerScript() } ) {
                div {
                    classes = setOf(CLS_REPORTS_SECTION)
                    div {
                        classes = setOf(CLS_BOOTSTRAP_CONTAINER_FLUID)
                        div {
                            classes = setOf(CLS_ROW, CLS_JUSTIFY_CONTENT_MD_CENTER)
                            div {
                                classes = setOf(CLS_COL_MD_AUTO)
                                form {
                                    id = FORM_ID_GRAPH
                                    classes = setOf(
                                        CLS_FORM_DATE_PICKER,
                                        CLS_BOOTSTRAP_FORM_INLINE,
                                        CLS_ML_2,
                                        CLS_MY_2,
                                        CLS_MY_LG_2
                                    )
                                    div {
                                        classes = setOf(CLS_BOOTSTRAP_INPUT_GROUP)
                                        input {
                                            classes = setOf(CLS_BOOTSTRAP_DATE_PICKER)
                                            dataDateFormat(PATTERN_DATE_PICKER)
                                            dataProvide("datepicker")
                                            name = GraphBlock::date1.name
                                            value = date1
                                        }
                                        input {
                                            classes = setOf(CLS_BOOTSTRAP_DATE_PICKER, CLS_ML_2)
                                            dataDateFormat(PATTERN_DATE_PICKER)
                                            dataProvide("datepicker")
                                            name = GraphBlock::date2.name
                                            value = date2
                                        }
                                        hiddenInput {
                                            name = GraphBlock::profileName.name
                                            value = page.profileName
                                        }
                                        submitInput {
                                            classes = setOf(BUTTON_BOOTSTRAP, BUTTON_BOOTSTRAP_SECONDARY, CLS_ML_2)
                                            value = "Подтвердить"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                br { }

                div {
                    id = STAT_GRAPH_BLOCK_OUTER
                    div {
                        id = STAT_GRAPH_BLOCK
                        createCharts(GraphBlock(page.profileName, date1, date2))
                    }
                }
            }
        }
    }
}