package com.benedetto.data.repository.remote.api

import com.benedetto.data.repository.remote.model.UserResponse
import retrofit2.http.GET

interface UserApiService {
    @GET("10")
    suspend fun getUsers(): List<UserResponse>
}
