package com.benedetto.modernandroid.di

import android.content.Context
import com.benedetto.data.repository.local.FakeTransactionRepository
import com.benedetto.data.repository.remote.auth.AuthInterceptor
import com.benedetto.data.repository.remote.auth.TokenManager
import com.benedetto.data.repository.remote.query.GraphQLRepository
import com.benedetto.data.repository.remote.repo.UserRepositoryImpl
import com.benedetto.core.repository.LaunchListRepository
import com.benedetto.core.repository.TransactionRepository
import com.benedetto.core.repository.UserRepository
import com.benedetto.core.usecase.GetLaunchListUseCase
import com.benedetto.core.usecase.GetTransactionsUseCase
import com.benedetto.core.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Key Takeaways:

    Provides Repository & UseCase as Dependencies
    @Singleton ensures only one instance exists
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesAuthInterceptorClient(context: Context): AuthInterceptor {
        return AuthInterceptor(TokenManager(context))
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(): TransactionRepository = FakeTransactionRepository()

    @Provides
    @Singleton
    fun provideUserRepository(authInterceptor: AuthInterceptor): UserRepository =
        UserRepositoryImpl(authInterceptor)

    @Provides
    @Singleton
    fun provideGraphQlRepository(): LaunchListRepository = GraphQLRepository()


    @Provides
    @Singleton
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactionsUseCase {
        return GetTransactionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLaunchListUseCase(repository: LaunchListRepository): GetLaunchListUseCase {
        return GetLaunchListUseCase(repository)
    }
}