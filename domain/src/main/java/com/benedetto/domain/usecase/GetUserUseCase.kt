package com.benedetto.domain.usecase

import com.benedetto.domain.model.User
import com.benedetto.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase (private val repository: UserRepository) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getUsers()
    }
}