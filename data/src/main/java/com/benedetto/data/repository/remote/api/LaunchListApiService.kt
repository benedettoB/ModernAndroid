package com.benedetto.data.repository.remote.api

import com.benedetto.data.LaunchListQuery

interface LaunchListApiService{
    suspend fun fetchLaunchList(): List<LaunchListQuery.Launch>
}