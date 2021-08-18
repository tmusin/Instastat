package ru.musintimur.instastat.model.entities

data class Comment(
    val commentId: Long,
    val postId: Long,
    val author: String,
    val text: String
)
