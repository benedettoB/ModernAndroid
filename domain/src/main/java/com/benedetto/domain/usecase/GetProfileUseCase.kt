package com.benedetto.domain.usecase

import com.benedetto.domain.model.Profile
import com.benedetto.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<List<Profile>> {
        return repository.getProfiles()
    }
}