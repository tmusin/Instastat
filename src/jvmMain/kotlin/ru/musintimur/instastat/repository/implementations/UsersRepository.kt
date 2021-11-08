package ru.musintimur.instastat.repository.implementations

import ru.musintimur.instastat.database.InstastatDatabaseQueries
import ru.musintimur.instastat.model.entities.User
import ru.musintimur.instastat.repository.contracts.UsersContract

class UsersRepository(private val queries: InstastatDatabaseQueries) : UsersContract {

    override suspend fun getUserByName(userName: String): User? =
        queries.getUserByName(userName).executeAsOneOrNull()?.let {
            User(it.user_id, it.user_name, it.password_hash, it.usergroup_id)
        }

    override suspend fun checkCredentials(userName: String, passwordHash: String): User? =
        queries.checkCredentials(userName, passwordHash).executeAsOneOrNull()?.let {
            User(it.user_id, it.user_name, it.password_hash, it.usergroup_id)
        }
}