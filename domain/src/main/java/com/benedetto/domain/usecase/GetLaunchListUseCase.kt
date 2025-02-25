package com.benedetto.domain.usecase

import com.benedetto.domain.model.LaunchWrapper
import com.benedetto.domain.repository.LaunchListRepository
import kotlinx.coroutines.flow.Flow

class GetLaunchListUseCase(private val repository: LaunchListRepository) {
    operator fun invoke(): Flow<List<LaunchWrapper>> {
        return repository.getLaunchList()
    }
}