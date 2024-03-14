package com.tes.presentation.mypage

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tes.presentation.login.navigateToLogin

@Composable
fun MyPageScreen(navController: NavController) {
    TextButton(onClick = { navController.navigateToLogin() }, content = { Text(text = "마이페이지") })
}
