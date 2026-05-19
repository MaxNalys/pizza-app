package com.dev.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dev.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {

    this.navigate(HomeRoute, navOptions)
}

fun NavGraphBuilder.composableHomeRoute() {

    composable<HomeRoute> {
        HomeScreen()
    }
}