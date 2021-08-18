package ru.musintimur.instastat.repository.contracts

import ru.musintimur.instastat.model.entities.Comment
import ru.musintimur.instastat.model.entities.Post

interface CommentsContract {

    suspend fun getPostComments(post: Post): List<Comment>
    suspend fun addNewComment(post: Post, commentAuthor: String, commentText: String)
    suspend fun findComment(post: Post, commentAuthor: String, commentText: String): Comment?
    suspend fun calculateComments(post: Post): Long

}