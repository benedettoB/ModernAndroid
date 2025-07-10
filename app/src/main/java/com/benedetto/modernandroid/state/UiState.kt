package com.benedetto.modernandroid.state

import com.benedetto.domain.model.Profile

// Sealed class to represent UI state
sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(val profiles: List<Profile>) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}