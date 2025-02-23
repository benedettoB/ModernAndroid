package com.benedetto.domain

/*Key Takeaways:

    Separation of Concerns → Business logic in UseCase
    Repository Interface → Allows for dependency injection
*/
data class Transaction(val id: Int, val amount: Double, val description: String)

interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
}

class GetTransactionsUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(): List<Transaction> {
        return repository.getTransactions()
    }
}
