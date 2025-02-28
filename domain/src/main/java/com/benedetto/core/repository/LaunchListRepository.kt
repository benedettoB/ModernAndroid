package com.benedetto.core.repository

import com.benedetto.core.model.LaunchWrapper
import kotlinx.coroutines.flow.Flow

interface LaunchListRepository {
    fun getLaunchList(): Flow<List<LaunchWrapper>>
}