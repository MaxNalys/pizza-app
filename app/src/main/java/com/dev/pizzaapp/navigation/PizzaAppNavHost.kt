package com.dev.pizzaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dev.home.navigation.HomeRoute
import com.dev.home.navigation.composableHomeRoute
import com.dev.splash.navigation.SplashRoute
import com.dev.splash.navigation.composableSplashRoute

@Composable
fun PizzaAppNavHost(

    navController: NavHostController,
    modifier: Modifier = Modifier,
    ) {

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = modifier,
        ) {

        composableSplashRoute(
            onFinish = {
                navController.navigate(HomeRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            }
        )
        composableHomeRoute()
    }
}