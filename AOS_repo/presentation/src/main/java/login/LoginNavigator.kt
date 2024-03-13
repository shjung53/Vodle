package com.tes.presentation.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tes.presentation.navigation.Destination

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(Destination.LOGIN.destination, navOptions)
}

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(Destination.LOGIN.destination) {
        LoginScreen(navController)
    }
}
