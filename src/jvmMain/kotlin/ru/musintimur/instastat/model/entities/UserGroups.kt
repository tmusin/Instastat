package ru.musintimur.instastat.model.entities

import ru.musintimur.instastat.web.auth.hash

enum class UserGroups(val userGroupId: Int) {
    UNKNOWN(0),
    ADMIN(1),
    OWNER(2),
    USER(3),
    GUEST(4);

    companion object {
        val signedInUserGroups = setOf(
            hash(ADMIN.toString()),
            hash(OWNER.toString()),
            hash(USER.toString()),
        )
    }
}