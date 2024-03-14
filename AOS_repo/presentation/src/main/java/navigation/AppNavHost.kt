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
        startDestination = Route.LOGIN.destination
    ) {
        composable(Route.LOGIN.destination) {
            LoginScreen { navController.navigateToDestination(Route.MAIN) }
        }

        composable(Route.MAIN.destination) {
            MainScreen { navController.navigateToDestination(Route.MY_PAGE) }
        }

        composable(Route.MY_PAGE.destination) {
            MyPageScreen { navController.navigateToDestination(Route.LOGIN) }
        }
    }
}

fun NavController.navigateToDestination(route: Route, navOptions: NavOptions? = null) {
    this.navigate(route.destination, navOptions)
}
