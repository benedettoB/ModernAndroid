package com.benedetto.modernandroid.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.core.usecase.GetUserUseCase
import com.benedetto.modernandroid.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _usersUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val usersUiState: StateFlow<UserUiState> = _usersUiState.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        if (!checkInternetConnection()) {
            _usersUiState.value = UserUiState.Error("No internet connection")
            return
        }
        _usersUiState.value = UserUiState.Loading
        viewModelScope.launch {
            try {
                getUserUseCase().collect { users ->
                    _usersUiState.value = UserUiState.Success(users)
                }

            } catch (e: Exception) {
                _usersUiState.value = UserUiState.Error(
                    message = e.localizedMessage ?: "Unknown error"
                )
                Log.e("UserViewModel", "Error fetching users", e)
            }
        }
    }

    fun retry() {
        fetchUsers()
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
