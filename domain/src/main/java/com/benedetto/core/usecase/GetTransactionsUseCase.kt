package com.benedetto.core.usecase

import com.benedetto.core.model.Transaction
import com.benedetto.core.repository.TransactionRepository

class GetTransactionsUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(): List<Transaction> {
        return repository.getTransactions()
    }
}
