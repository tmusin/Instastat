package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.CLS_BOOTSTRAP_TABLE
import ru.musintimur.instastat.common.constants.ICON_INSTAGRAM
import ru.musintimur.instastat.common.constants.CLS_MINI_ICON
import ru.musintimur.instastat.extensions.setColoredCell
import ru.musintimur.instastat.model.entities.PeriodReportRecord
import ru.musintimur.instastat.parsers.getFullProfileUrl
import ru.musintimur.instastat.web.pages.PROFILE_PAGE

fun DIV.statTableReport(data: List<PeriodReportRecord>) {
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
            data.forEach { record ->
                tr {
                    td {
                        a {
                            href = getFullProfileUrl(record.title)
                            target = "_blank"
                            img {
                                src = ICON_INSTAGRAM
                                classes = setOf(CLS_MINI_ICON)
                            }
                        }
                        a {
                            href = "$PROFILE_PAGE/${record.title}/"
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