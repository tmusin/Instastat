package ru.musintimur.instastat.repository

import ru.musintimur.instastat.database.InstastatDatabase
import ru.musintimur.instastat.repository.contracts.ProfilesContract
import ru.musintimur.instastat.repository.contracts.ProfilesHistoryContract
import ru.musintimur.instastat.repository.implementations.ProfilesHistoryRepository
import ru.musintimur.instastat.repository.implementations.ProfilesRepository

class InstastatRepository(db: InstastatDatabase) : Repository {

    override val profiles: ProfilesContract
    override val profilesHistory: ProfilesHistoryContract

    init {
        profiles = ProfilesRepository(db.instastatDatabaseQueries)
        profilesHistory = ProfilesHistoryRepository(db.instastatDatabaseQueries)
    }
}