package ru.musintimur.instastat.model.entities

import io.ktor.auth.*

data class User (
    val userId: Int,
    val userName: String,
    val passwordHash: String,
    val userGroupId: Int,
    val token: String? = null
) : Principal {
    fun getGroup() : UserGroups =
        UserGroups.values().firstOrNull { it.userGroupId == this.userGroupId } ?: UserGroups.UNKNOWN
}