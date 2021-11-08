package ru.musintimur.instastat.web.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import ru.musintimur.instastat.model.entities.User
import java.util.*

const val CLAIM_USER_NAME = "username"

const val JWT_SERVICE_ADMIN = "jwt_admin"
const val JWT_SERVICE_OWNER = "jwt_owner"
const val JWT_SERVICE_USER = "jwt_user"
const val JWT_SERVICE_GUEST = "jwt_guest"

object JWTService {

    private const val issuer: String = "instastat"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim(CLAIM_USER_NAME, user.userName)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun expiresAt() = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }.time
}