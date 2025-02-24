package com.benedetto.domain.usecase

import com.benedetto.domain.model.Transaction
import com.benedetto.domain.repository.TransactionRepository

class GetTransactionsUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(): List<Transaction> {
        return repository.getTransactions()
    }
}
