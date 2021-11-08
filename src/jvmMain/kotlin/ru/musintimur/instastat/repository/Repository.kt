package ru.musintimur.instastat.repository

import ru.musintimur.instastat.repository.contracts.*

interface Repository {

    val profiles: ProfilesContract
    val profilesHistory: ProfilesHistoryContract
    val posts: PostsContract
    val comments: CommentsContract
    val users: UsersContract

}