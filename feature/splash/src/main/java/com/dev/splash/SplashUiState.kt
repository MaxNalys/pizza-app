package com.dev.splash

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data object Finished : SplashUiState
}