package com.benedetto.data.repository.remote.auth

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT

class TokenManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("access_token", token).apply()
    }

    fun getToken(): String? = prefs.getString("access_token", null)

    fun isTokenValid(): Boolean {
        val token = getToken() ?: return false
        return try {
            val jwt = JWT(token)
            !jwt.isExpired(0)
        } catch (e: Exception) {
            false
        }
    }
}
