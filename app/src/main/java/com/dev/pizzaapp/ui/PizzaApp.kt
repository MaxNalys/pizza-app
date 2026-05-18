package com.dev.pizzaapp.ui

import androidx.compose.runtime.Composable

import androidx.navigation.compose.rememberNavController

import com.dev.designsystem.component.AppBackground

import com.dev.designsystem.theme.PizzaAppTheme

import com.dev.pizzaapp.navigation.PizzaAppNavHost

@Composable

fun PizzaApp() {

    val navController = rememberNavController()

    PizzaAppTheme {
        AppBackground {
            PizzaAppNavHost(
                navController = navController
            )
        }
    }
}