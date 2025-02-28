package com.benedetto.core.usecase

import com.benedetto.core.model.User
import com.benedetto.core.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase (private val repository: UserRepository) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getUsers()
    }
}