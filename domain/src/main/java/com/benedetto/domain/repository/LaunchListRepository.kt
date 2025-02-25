package com.benedetto.domain.repository

import com.benedetto.domain.model.LaunchWrapper
import kotlinx.coroutines.flow.Flow

interface LaunchListRepository {
    fun getLaunchList(): Flow<List<LaunchWrapper>>
}