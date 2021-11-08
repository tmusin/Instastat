package ru.musintimur.instastat.web.auth

data class InstastatSession (
    val username: String,
    val userGroupHash: String,
    val token: String,
    val expireDate: Long
)