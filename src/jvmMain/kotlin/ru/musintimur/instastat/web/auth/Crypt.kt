package ru.musintimur.instastat.web.auth

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM = "HmacSHA1"

val hashKey = hex(System.getenv("HASH_KEY"))

val hMacKey = SecretKeySpec(hashKey, ALGORITHM)

fun hash(password: String): String {
    val hMac = Mac.getInstance(ALGORITHM)
    hMac.init(hMacKey)
    return hex(hMac.doFinal(password.toByteArray(Charsets.UTF_8)))
}