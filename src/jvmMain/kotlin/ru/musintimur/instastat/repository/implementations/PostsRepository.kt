package ru.musintimur.instastat.repository.implementations

import ru.musintimur.instastat.database.InstastatDatabaseQueries
import ru.musintimur.instastat.extensions.asSqlLiteDateTime
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.repository.contracts.PostsContract

class PostsRepository(private val queries: InstastatDatabaseQueries): PostsContract {

    override suspend fun getAllPosts(): List<Post> =
        queries.getAllPosts().executeAsList().map {
            Post(it.post_id, it.post_url, it.post_date.asSqlLiteDateTime(), it.post_text, it.comments_count.toInt())
        }

    override suspend fun getPostByUrl(url: String): Post? =
        queries.getPostByUrl(url).executeAsOneOrNull()?.let {
            Post(it.post_id, it.post_url, it.post_date.asSqlLiteDateTime(), it.post_text, it.comments_count.toInt())
        }

    override suspend fun addPost(url: String) {
        queries.insertNewPost(url)
    }
}