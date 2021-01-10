package ru.musintimur.instastat.repository.contracts

import ru.musintimur.instastat.model.entities.Profile

interface ProfilesContract {

    suspend fun getAllProfiles(): List<Profile>
    suspend fun getAllActiveProfiles(): List<Profile>
    suspend fun getAllActiveProfilesForUpdate(): List<Profile>
    suspend fun getParserProgress(): Int
    suspend fun getProfileByName(profileName: String): Profile?

}