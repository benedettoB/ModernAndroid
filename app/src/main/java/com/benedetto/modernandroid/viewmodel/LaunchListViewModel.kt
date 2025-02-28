package com.benedetto.modernandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.core.model.LaunchWrapper
import com.benedetto.core.usecase.GetLaunchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LaunchListViewModel @Inject constructor(private val getLaunchListUseCase: GetLaunchListUseCase) :
    ViewModel() {

    private val _launchList: MutableStateFlow<List<LaunchWrapper>> = MutableStateFlow(emptyList())
    val launchList: StateFlow<List<LaunchWrapper>> = _launchList


    init {
        fetchLaunchList()
    }

    private fun fetchLaunchList() {
        viewModelScope.launch {
            getLaunchListUseCase()
                .catch { e ->
                    e.localizedMessage?.let {
                        Log.d("LaunchListViewModel", it)
                    }
                }.buffer()
                .collect { launchWrapperList ->
                    _launchList.value = launchWrapperList
                }
        }
    }
}