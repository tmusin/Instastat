package ru.musintimur.instastat.repository.implementations

import ru.musintimur.instastat.database.InstastatDatabaseQueries
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.repository.contracts.ProfilesContract

class ProfilesRepository(private val queries: InstastatDatabaseQueries) : ProfilesContract {

    override suspend fun getAllProfiles(): List<Profile> =
        queries.getAllProfiles().executeAsList().map {
            Profile(it.profile_id, it.profile_name)
        }

    override suspend fun getAllActiveProfiles(): List<Profile> =
        queries.getAllActiveProfiles().executeAsList().map {
            Profile(it.profile_id, it.profile_name)
        }

    override suspend fun getAllActiveProfilesForUpdate(): List<Profile> =
        queries.getAllActiveProfilesForUpdate().executeAsList().map {
            Profile(it.profile_id, it.profile_name)
        }

    override suspend fun getParserProgress(): Int =
        100 - (getAllActiveProfilesForUpdate().size.toFloat() / getAllActiveProfiles().size.toFloat() * 100f).toInt()

    override suspend fun getProfileByName(profileName: String): Profile? =
         queries.getProfileByName(profileName.toLowerCase()).executeAsList().firstOrNull()?.let {
             Profile(it.profile_id, it.profile_name)
         }

}