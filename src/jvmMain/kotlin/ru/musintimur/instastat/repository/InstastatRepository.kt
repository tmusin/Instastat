package ru.musintimur.instastat.repository

import ru.musintimur.instastat.database.InstastatDatabase
import ru.musintimur.instastat.repository.contracts.*
import ru.musintimur.instastat.repository.implementations.*

class InstastatRepository(db: InstastatDatabase) : Repository {

    override val profiles: ProfilesContract
    override val profilesHistory: ProfilesHistoryContract
    override val posts: PostsContract
    override val comments: CommentsContract
    override val users: UsersContract

    init {
        profiles = ProfilesRepository(db.instastatDatabaseQueries)
        profilesHistory = ProfilesHistoryRepository(db.instastatDatabaseQueries)
        posts = PostsRepository(db.instastatDatabaseQueries)
        comments = CommentsRepository(db.instastatDatabaseQueries)
        users = UsersRepository(db.instastatDatabaseQueries)
    }
}