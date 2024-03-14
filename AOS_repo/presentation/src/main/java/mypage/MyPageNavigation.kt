package com.tes.presentation.mypage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tes.presentation.navigation.Destination

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(Destination.MY_PAGE.destination, navOptions)
}

fun NavGraphBuilder.myPageScreen(navController: NavController) {
    composable(Destination.MY_PAGE.destination) {
        MyPageScreen(navController)
    }
}
