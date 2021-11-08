package ru.musintimur.instastat.model.entities

data class PeriodReportRecord(
    val title: String,
    val count_posts: Int,
    val count_posts_diff: Int,
    val count_followers: Int,
    val count_followers_diff: Int,
    val count_followings: Int,
    val count_followings_diff: Int
)
