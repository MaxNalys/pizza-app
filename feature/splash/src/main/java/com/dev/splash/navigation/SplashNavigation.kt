package com.dev.splash.navigation

import androidx.navigation.NavController

import androidx.navigation.NavGraphBuilder

import androidx.navigation.NavOptions

import androidx.navigation.compose.composable
import com.dev.splash.SplashScreen


import kotlinx.serialization.Serializable

@Serializable

data object SplashRoute

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {

    navigate(SplashRoute, navOptions)

}

fun NavGraphBuilder.composableSplashRoute(
    onFinish: () -> Unit
) {
    composable<SplashRoute> {
        SplashScreen(
            onFinish = onFinish
        )
    }
}