package com.benedetto.geniusbankinterview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.domain.GetTransactionsUseCase
import com.benedetto.domain.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*Key Takeaways:

    Uses StateFlow for state management
    Calls GetTransactionsUseCase inside ViewModel
    @Inject constructor() makes Hilt automatically inject dependencies.
*/
@HiltViewModel
class TransactionViewModel @Inject constructor(private val getTransactionsUseCase: GetTransactionsUseCase) :
    ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    init {
        fetchTransactions()
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            _transactions.value = getTransactionsUseCase()
        }
    }
}
