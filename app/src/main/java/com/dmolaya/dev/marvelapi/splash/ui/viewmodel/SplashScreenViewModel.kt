package com.dmolaya.dev.marvelapi.splash.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {
    private val _navigateToCharacters = MutableStateFlow(false)
    val navigateToCharacters: StateFlow<Boolean> = _navigateToCharacters.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500) // Simulates data load
            _navigateToCharacters.value = true
        }
    }
}