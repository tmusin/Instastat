package ru.musintimur.instastat.model.entities

data class Comment(
    val commentId: Int,
    val postId: Int,
    val author: String,
    val text: String
)
