package ru.musintimur.instastat.model.entities

import java.time.LocalDateTime

data class ProfileHistory(
    val profilesHistoryId: Int,
    val profileId: Int,
    val meteringDate: LocalDateTime,
    val countPosts: Int,
    val countFollowers: Int,
    val countFollowings: Int
)
