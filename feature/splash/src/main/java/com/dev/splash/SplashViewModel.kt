package com.dev.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel : ViewModel() {

    private val _state = MutableStateFlow<SplashUiState>(SplashUiState.Loading)

    val state = _state.asStateFlow()

    fun onAnimationFinished() {

        _state.value = SplashUiState.Finished

    }

}