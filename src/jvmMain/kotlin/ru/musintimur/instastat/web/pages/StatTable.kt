package ru.musintimur.instastat.web.pages

import io.ktor.locations.*
import kotlinx.html.*
import ru.musintimur.instastat.api.DayReport
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.*
import ru.musintimur.instastat.model.entities.PeriodReportRecord
import ru.musintimur.instastat.web.components.statTableReport
import java.time.LocalDate

@KtorExperimentalLocationsAPI
fun BODY.statTable(date: LocalDate, report: List<PeriodReportRecord>, todayProgress: Int) {
    div {
        classes = setOf(CLS_REPORTS_SECTION)
        div {
            classes = setOf(CLS_BOOTSTRAP_CONTAINER_FLUID)
            div {
                classes = setOf(CLS_ROW, CLS_JUSTIFY_CONTENT_MD_CENTER)
                div {
                    classes = setOf(CLS_COL_MD_AUTO)
                    form {
                        id = FORM_ID_REPORT
                        classes = setOf(CLS_FORM_DATE_PICKER, CLS_BOOTSTRAP_FORM_INLINE, CLS_ML_2, CLS_MY_2, CLS_MY_LG_2)
                        div {
                            classes = setOf(CLS_BOOTSTRAP_INPUT_GROUP)
                            input {
                                classes = setOf(CLS_BOOTSTRAP_DATE_PICKER)
                                dataDateFormat(PATTERN_DATE_PICKER)
                                dataProvide("datepicker")
                                name = DayReport::dateOfReport.name
                                value = date.toSqlLiteText()
                            }
                            hiddenInput {
                                name = DayReport::sortOrdering.name
                                value = DROPDOWN_SORT_TYPE_AZ
                            }
                            submitInput {
                                classes = setOf(BUTTON_BOOTSTRAP, BUTTON_BOOTSTRAP_SECONDARY, CLS_ML_2)
                                value = "Отчёт"
                            }
                        }
                    }
                }
                if (todayProgress < 100) {
                    div {
                        classes = setOf(CLS_COL_MD_AUTO)
                        form {
                            id = FORM_ID_PARSING
                            classes =
                                setOf(
                                    CLS_FORM_START_PARSING,
                                    CLS_BOOTSTRAP_FORM_INLINE,
                                    CLS_ML_2,
                                    CLS_MT_2,
                                    CLS_MY_2,
                                    CLS_MY_LG_2
                                )
                            submitInput {
                                classes = setOf(BUTTON_BOOTSTRAP, BUTTON_BOOTSTRAP_SECONDARY, CLS_ML_2)
                                value = "Пуск"
                            }

                        }
                    }
                    div {
                        classes = setOf(CLS_COL)
                        div {
                            classes = setOf(CLS_PROGRESS, CLS_PROGRESS_PARSING)
                            style = "visibility: hidden;"
                            div {
                                classes = setOf(CLS_PROGRESS_BAR, CLS_PROGRESS_BAR_STRIPPED, CLS_PROGRESS_BAR_ANIMATED)
                                role = "progressbar"
                                style = "width: $todayProgress%"
                                ariaValueMin(0)
                                ariaValueMax(100)
                                ariaValueNow(todayProgress)
                            }
                        }
                    }
                }
            }
        }

        br { }

        div {
            id = STAT_REPORT_BLOCK_OUTER

            div {
                id = STAT_REPORT_BLOCK
                statTableReport(report)
            }
        }
    }
}