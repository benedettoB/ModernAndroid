package com.benedetto.data.repository.remote.auth

import android.content.Context
import com.auth0.android.jwt.JWT

class TokenManager(context: Context) {
    internal val token: String = "SOME_TOKEN_#$#$#_FROM_AUTH_API"

    fun isTokenValid(): Boolean {
        return try {
            val jwt = JWT(token)
            !jwt.isExpired(0)
        } catch (e: Exception) {
            false
        }
    }
}
