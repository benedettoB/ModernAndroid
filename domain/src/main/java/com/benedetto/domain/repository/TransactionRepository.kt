package com.benedetto.domain.repository

import com.benedetto.domain.model.Transaction

/*Key Takeaways:

    Separation of Concerns → Business logic in UseCase
    Repository Interface → Allows for dependency injection
*/
interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
}

