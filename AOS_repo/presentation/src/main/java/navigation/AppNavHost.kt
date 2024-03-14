package com.tes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tes.presentation.login.LoginScreen
import com.tes.presentation.main.MainScreen
import com.tes.presentation.mypage.MyPageScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LOGIN.destination
    ) {
        composable(Destination.LOGIN.destination) {
            LoginScreen { navController.navigateToDestination(Destination.MAIN) }
        }

        composable(Destination.MAIN.destination) {
            MainScreen { navController.navigateToDestination(Destination.MY_PAGE) }
        }

        composable(Destination.MY_PAGE.destination) {
            MyPageScreen { navController.navigateToDestination(Destination.LOGIN) }
        }
    }
}

fun NavController.navigateToDestination(destination: Destination, navOptions: NavOptions? = null) {
    this.navigate(destination.destination, navOptions)
}
