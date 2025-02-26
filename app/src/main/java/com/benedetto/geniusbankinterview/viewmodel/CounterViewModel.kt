package com.benedetto.geniusbankinterview.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


// ViewModel using StateFlow
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun increment() {
        _count.value += 1
    }
}

