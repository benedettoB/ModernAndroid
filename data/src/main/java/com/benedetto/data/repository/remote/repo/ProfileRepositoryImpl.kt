package com.benedetto.data.repository.remote.repo


import android.util.Log
import com.benedetto.data.repository.remote.api.ProfileApiService
import com.benedetto.data.repository.remote.mapper.toDomain
import com.benedetto.data.repository.remote.model.ProfileResponse
import com.benedetto.domain.model.Profile
import com.benedetto.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRepositoryImpl() :
    ProfileRepository {

    override fun getProfiles(): Flow<List<Profile>> = flow {
        val profilesResponse: List<ProfileResponse> = fetchProfiles()
        val profile: List<Profile> = profilesResponse.asSequence().map { it.toDomain() }
            .toList() //use as sequence for optimized performance when mapping or filtering lists
        emit(profile)
    }.flowOn(Dispatchers.IO) //ensures code runs off the main thread on background threads optimized for network activities such as this


    private val api: ProfileApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApiService::class.java)
    }

    private suspend fun fetchProfiles(): List<ProfileResponse> {

        return try {
            api.getProfiles()
        } catch (exception: Exception) {
            Log.e("UserRepositoryImpl", "error: ${exception.localizedMessage}")
            emptyList()
        }
    }


}

