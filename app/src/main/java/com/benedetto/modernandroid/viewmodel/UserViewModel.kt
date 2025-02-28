package com.benedetto.modernandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.core.model.User
import com.benedetto.core.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _usersList: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val usersList: StateFlow<List<User>> = _usersList

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            getUserUseCase()
                .catch { e ->
                    e.localizedMessage?.let {
                        Log.d("UserViewModel", it)
                    }
                }
                .buffer()
                .collect { users ->
                    _usersList.value = users
                }
        }
    }
}