package com.benedetto.core.usecase

import com.benedetto.core.model.LaunchWrapper
import com.benedetto.core.repository.LaunchListRepository
import kotlinx.coroutines.flow.Flow

class GetLaunchListUseCase(private val repository: LaunchListRepository) {
    operator fun invoke(): Flow<List<LaunchWrapper>> {
        return repository.getLaunchList()
    }
}