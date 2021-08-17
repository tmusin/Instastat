package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.padZeros
import ru.musintimur.instastat.extensions.setColoredCell
import ru.musintimur.instastat.model.entities.PeriodReportRecord
import ru.musintimur.instastat.parsers.getFullProfileUrl
import ru.musintimur.instastat.web.pages.PROFILE_PAGE

fun DIV.statTableReport(data: List<PeriodReportRecord>,
                        sortOrder: String = DROPDOWN_SORT_TYPE_AZ
) {
    customDropdown(sortDropdownMap, sortDropdownMap[sortOrder] ?: DROPDOWN_SORT_AZ)
    br { }
    val sortedReport: List<PeriodReportRecord> = when(sortOrder) {
        DROPDOWN_SORT_TYPE_AZ -> data.sortedBy { it.title }

        DROPDOWN_SORT_TYPE_SPMAX -> data.sortedByDescending { it.count_posts }
        DROPDOWN_SORT_TYPE_SPMIN -> data.sortedBy { it.count_posts }
        DROPDOWN_SORT_TYPE_NSPMAX -> data.sortedByDescending { it.count_posts_diff }
        DROPDOWN_SORT_TYPE_MSPMIN -> data.sortedBy { it.count_posts_diff }

        DROPDOWN_SORT_TYPE_SFLWRMAX -> data.sortedByDescending { it.count_followers }
        DROPDOWN_SORT_TYPE_SFFLWRMIN -> data.sortedBy { it.count_followers }
        DROPDOWN_SORT_TYPE_NSFLWRMAX -> data.sortedByDescending { it.count_followers_diff }
        DROPDOWN_SORT_TYPE_NSFLWRMIN -> data.sortedBy { it.count_followers_diff }

        DROPDOWN_SORT_TYPE_SFLWGMAX -> data.sortedByDescending { it.count_followings }
        DROPDOWN_SORT_TYPE_SFLWGMIN -> data.sortedBy { it.count_followings }
        DROPDOWN_SORT_TYPE_NSFLWGMAX -> data.sortedByDescending { it.count_followings_diff }
        DROPDOWN_SORT_TYPE_MSFLWGMIN -> data.sortedBy { it.count_followings_diff }
        else -> data
    }
    div {
        table {
            attributes["border"] = "1"
            classes = setOf(CLS_BOOTSTRAP_TABLE)
            tr {
                th { +"Profile" }
                th { +"Posts" }
                th { +"Followers" }
                th { +"Followings" }
            }
            sortedReport.forEachIndexed { index, record ->
                tr {
                    td {
                        +"${index.inc().padZeros(2)}."
                        a {
                            href = getFullProfileUrl(record.title)
                            target = "_blank"
                            img {
                                src = ICON_INSTAGRAM
                                classes = setOf(CLS_MINI_ICON)
                            }
                        }
                        a {
                            href = "$PROFILE_PAGE/${record.title}"
                            target = "_blank"
                            +record.title
                        }
                    }
                    td {
                        setColoredCell(record.count_posts, record.count_posts_diff)
                    }
                    td {
                        setColoredCell(record.count_followers, record.count_followers_diff)
                    }
                    td {
                        setColoredCell(record.count_followings, record.count_followings_diff)
                    }
                }
            }
        }
    }
}