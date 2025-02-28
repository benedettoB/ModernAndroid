package com.benedetto.core.repository

import com.benedetto.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
}
