package ru.musintimur.instastat.model.entities

import java.time.LocalDateTime

data class Post(
    val postId: Int,
    val postUrl: String,
    val postDate: LocalDateTime?,
    val postText: String?,
    val commentsCount: Int
)
