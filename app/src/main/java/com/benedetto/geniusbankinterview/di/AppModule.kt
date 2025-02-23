package com.benedetto.geniusbankinterview.di

import com.benedetto.data.FakeTransactionRepository
import com.benedetto.domain.GetTransactionsUseCase
import com.benedetto.domain.TransactionRepository
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
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactionsUseCase {
        return GetTransactionsUseCase(repository)
    }
}
