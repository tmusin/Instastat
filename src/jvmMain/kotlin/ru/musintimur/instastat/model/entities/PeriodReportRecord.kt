package ru.musintimur.instastat.model.entities

data class PeriodReportRecord(
    val title: String,
    val count_posts: Long,
    val count_posts_diff: Long,
    val count_followers: Long,
    val count_followers_diff: Long,
    val count_followings: Long,
    val count_followings_diff: Long
)
