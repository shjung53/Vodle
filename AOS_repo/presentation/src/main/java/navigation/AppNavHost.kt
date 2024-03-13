package com.tes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tes.presentation.login.loginScreen
import com.tes.presentation.main.mainScreen
import com.tes.presentation.mypage.myPageScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LOGIN.destination
    ) {
        loginScreen(navController)

        mainScreen(navController)

        myPageScreen(navController)
    }
}
