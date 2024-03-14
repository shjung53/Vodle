package com.tes.presentation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tes.presentation.navigation.Destination

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(Destination.MAIN.destination, navOptions)
}

fun NavGraphBuilder.mainScreen(navController: NavController) {
    composable(Destination.MAIN.destination) {
        MainScreen(navController)
    }
}
