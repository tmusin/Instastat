package ru.musintimur.instastat.repository.contracts

import ru.musintimur.instastat.model.entities.Post

interface PostsContract {

    suspend fun getAllPosts(): List<Post>
    suspend fun getPostByUrl(url: String): Post?
    suspend fun addPost(url: String)

}