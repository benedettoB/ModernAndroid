package com.benedetto.data.repository.remote.api

import com.benedetto.data.repository.remote.model.ProfileResponse
import retrofit2.http.GET

fun interface ProfileApiService{
    @GET("posts/1/comments")
    suspend fun getProfiles(): List<ProfileResponse>
}