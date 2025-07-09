package com.benedetto.modernandroid.state

import com.benedetto.core.model.User

sealed class UserUiState {
    data object Loading : UserUiState()
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}