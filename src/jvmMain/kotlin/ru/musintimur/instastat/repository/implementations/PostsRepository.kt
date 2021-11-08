package ru.musintimur.instastat.repository.implementations

import ru.musintimur.instastat.database.InstastatDatabaseQueries
import ru.musintimur.instastat.extensions.asSqlLiteDateTime
import ru.musintimur.instastat.extensions.toSqlLiteText
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.repository.contracts.PostsContract
import java.time.LocalDateTime

class PostsRepository(private val queries: InstastatDatabaseQueries): PostsContract {

    override suspend fun getAllPosts(): List<Post> =
        queries.getAllPosts().executeAsList().map {
            Post(it.post_id, it.post_url, it.post_date.asSqlLiteDateTime(), it.post_text, it.comments_count)
        }

    override suspend fun getPostByUrl(url: String): Post? =
        queries.getPostByUrl(url).executeAsOneOrNull()?.let {
            Post(it.post_id, it.post_url, it.post_date.asSqlLiteDateTime(), it.post_text, it.comments_count)
        }

    override suspend fun getPostById(postId: Int): Post? =
        queries.getPostById(postId).executeAsOneOrNull()?.let {
            Post(it.post_id, it.post_url, it.post_date.asSqlLiteDateTime(), it.post_text, it.comments_count)
        }

    override suspend fun addPost(url: String) {
        queries.insertNewPost(url)
    }

    override suspend fun updateDateTime(url: String, datetime: LocalDateTime) {
        queries.updatePostDateTime(datetime.toSqlLiteText(), url)
    }

    override suspend fun updateText(url: String, text: String) {
        queries.updatePostText(text, url)
    }

    override suspend fun updateCommentsCount(url: String, count: Int) {
        queries.updatePostCommentsCount(count, url)
    }
}