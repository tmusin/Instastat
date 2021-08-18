package ru.musintimur.instastat.repository.implementations

import ru.musintimur.instastat.database.InstastatDatabaseQueries
import ru.musintimur.instastat.model.entities.Comment
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.repository.contracts.CommentsContract

class CommentsRepository(private val queries: InstastatDatabaseQueries): CommentsContract {

    override suspend fun getPostComments(post: Post): List<Comment> =
        queries.getPostComments(post.postId).executeAsList().map {
            Comment(it.comment_id, it.post_id, it.comment_author, it.comment_text)
        }

    override suspend fun addNewComment(post: Post, commentAuthor: String, commentText: String) {
        queries.insertNewComment(post.postId, commentAuthor, commentText)
    }

    override suspend fun findComment(post: Post, commentAuthor: String, commentText: String): Comment? =
        queries.findComment(post.postId, commentAuthor, commentText).executeAsList().firstOrNull()?.let {
            Comment(it.comment_id, it.post_id, it.comment_author, it.comment_text)
        }

    override suspend fun calculateComments(post: Post): Long =
        queries.calculatePostComments(post.postId).executeAsOne()
}