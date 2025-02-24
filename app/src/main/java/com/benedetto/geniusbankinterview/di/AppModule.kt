package com.benedetto.geniusbankinterview.di

import com.benedetto.data.repository.local.FakeTransactionRepository
import com.benedetto.data.repository.remote.UserRepositoryImpl
import com.benedetto.domain.repository.TransactionRepository
import com.benedetto.domain.repository.UserRepository
import com.benedetto.domain.usecase.GetTransactionsUseCase
import com.benedetto.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideTransactionRepository(): TransactionRepository = FakeTransactionRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

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


}







































