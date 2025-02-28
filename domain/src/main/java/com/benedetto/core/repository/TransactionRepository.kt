package com.benedetto.core.repository

import com.benedetto.core.model.Transaction

/*Key Takeaways:

    Separation of Concerns → Business logic in UseCase
    Repository Interface → Allows for dependency injection
*/
interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
}

