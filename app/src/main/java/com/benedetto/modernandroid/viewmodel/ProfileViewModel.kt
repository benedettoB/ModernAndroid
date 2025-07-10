package com.benedetto.modernandroid.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.domain.usecase.GetProfileUseCase
import com.benedetto.modernandroid.R
import com.benedetto.modernandroid.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchProfiles()
    }

    private fun fetchProfiles() {
        if (!checkInternetConnection()) {
            _uiState.value = ProfileUiState.Error(context.getString(R.string.no_internet))
            return
        }
        _uiState.value = ProfileUiState.Loading
        viewModelScope.launch {
            try {
                getProfileUseCase()
                    .collect { profiles ->
                        _uiState.value = ProfileUiState.Success(profiles)
                    }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(
                    message = e.localizedMessage ?: context.getString(R.string.unknown_error)
                )
                Log.e("ProfileViewModel", "Error fetching profiles", e)
            }
        }
    }

    fun retry() {
        fetchProfiles()
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