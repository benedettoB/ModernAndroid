package com.benedetto.data.repository.remote

import android.util.Log
import com.benedetto.data.repository.remote.api.UserApiService
import com.benedetto.data.repository.remote.mapper.toDomain
import com.benedetto.data.repository.remote.model.UserResponse
import com.benedetto.domain.model.User
import com.benedetto.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepositoryImpl : UserRepository {
    private val api: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/todos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }

    private suspend fun fetchUsers(): List<UserResponse> {

        return try {
            api.getUsers()
        } catch (exception: Exception) {
            Log.d("UserRepositoryImpl", "error: ${exception.localizedMessage}")
            emptyList()
        }
    }

    override fun getUsers(): Flow<List<User>> = flow {
        val userResponse = fetchUsers()
        val users = userResponse.asSequence().map { it.toDomain() }.toList()
        emit(users)
    }.flowOn(Dispatchers.IO)
}





















