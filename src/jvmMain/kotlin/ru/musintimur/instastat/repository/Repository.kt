package ru.musintimur.instastat.repository

import ru.musintimur.instastat.repository.contracts.PostsContract
import ru.musintimur.instastat.repository.contracts.ProfilesContract
import ru.musintimur.instastat.repository.contracts.ProfilesHistoryContract

interface Repository {

    val profiles: ProfilesContract
    val profilesHistory: ProfilesHistoryContract
    val posts: PostsContract

}