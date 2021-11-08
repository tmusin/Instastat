package ru.musintimur.instastat.repository.contracts

import ru.musintimur.instastat.model.entities.User

interface UsersContract {

    suspend fun getUserByName(userName: String): User?
    suspend fun checkCredentials(userName: String, passwordHash: String): User?

}