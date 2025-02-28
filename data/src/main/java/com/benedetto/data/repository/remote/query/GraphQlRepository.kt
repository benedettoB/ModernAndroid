package com.benedetto.data.repository.remote.query

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.benedetto.data.LaunchListQuery
import com.benedetto.data.repository.remote.api.LaunchListApiService
import com.benedetto.data.repository.remote.mapper.toDomain
import com.benedetto.core.model.LaunchWrapper
import com.benedetto.core.repository.LaunchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GraphQLRepository : LaunchListApiService, LaunchListRepository {

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .build()


    override fun getLaunchList(): Flow<List<LaunchWrapper>> = flow {
        val queryResponse = fetchLaunchList()
        val launchWrapper =
            if (queryResponse.isEmpty()) emptyList() else queryResponse.asSequence()
                .map { it.toDomain() }.toList()
        emit(launchWrapper)
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchLaunchList(): List<LaunchListQuery.Launch> {
        return try {
            val response = apolloClient.query(LaunchListQuery()).execute()
            return response.data?.launches?.launches?.filterNotNull() ?: emptyList()

        } catch (exception: Exception) {
            Log.d("GraphQlRepository", "error: ${exception.localizedMessage}")
            emptyList()
        }

    }
}
