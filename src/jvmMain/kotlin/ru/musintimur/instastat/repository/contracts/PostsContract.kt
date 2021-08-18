package ru.musintimur.instastat.repository.contracts

import ru.musintimur.instastat.model.entities.Post
import java.time.LocalDateTime

interface PostsContract {

    suspend fun getAllPosts(): List<Post>
    suspend fun getPostByUrl(url: String): Post?
    suspend fun getPostById(postId: Long): Post?
    suspend fun addPost(url: String)
    suspend fun updateDateTime(url: String, datetime: LocalDateTime)
    suspend fun updateText(url: String, text: String)
    suspend fun updateCommentsCount(url: String, count: Long)

}