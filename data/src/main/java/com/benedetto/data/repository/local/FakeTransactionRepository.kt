package com.benedetto.data.repository.local


import com.benedetto.domain.model.Transaction
import com.benedetto.domain.repository.TransactionRepository
import kotlinx.coroutines.delay

/*Key Takeaways:

    Repository Implementation → Simulates fetching transactions (can later be replaced with real API calls)
    Uses delay(1000) → Mimics network latency
*/
class FakeTransactionRepository : TransactionRepository {
    override suspend fun getTransactions(): List<Transaction> {
        delay(1000) // Simulate network delay
        return listOf(
            Transaction(1, 150.0, "Deposit"),
            Transaction(2, -50.0, "Grocery Shopping"),
            Transaction(3, 200.0, "Freelance Payment")
        )
    }
}
