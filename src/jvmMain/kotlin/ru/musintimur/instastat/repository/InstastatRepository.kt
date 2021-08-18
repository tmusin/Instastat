package ru.musintimur.instastat.repository

import ru.musintimur.instastat.database.InstastatDatabase
import ru.musintimur.instastat.repository.contracts.CommentsContract
import ru.musintimur.instastat.repository.contracts.PostsContract
import ru.musintimur.instastat.repository.contracts.ProfilesContract
import ru.musintimur.instastat.repository.contracts.ProfilesHistoryContract
import ru.musintimur.instastat.repository.implementations.CommentsRepository
import ru.musintimur.instastat.repository.implementations.PostsRepository
import ru.musintimur.instastat.repository.implementations.ProfilesHistoryRepository
import ru.musintimur.instastat.repository.implementations.ProfilesRepository

class InstastatRepository(db: InstastatDatabase) : Repository {

    override val profiles: ProfilesContract
    override val profilesHistory: ProfilesHistoryContract
    override val posts: PostsContract
    override val comments: CommentsContract

    init {
        profiles = ProfilesRepository(db.instastatDatabaseQueries)
        profilesHistory = ProfilesHistoryRepository(db.instastatDatabaseQueries)
        posts = PostsRepository(db.instastatDatabaseQueries)
        comments = CommentsRepository(db.instastatDatabaseQueries)
    }
}