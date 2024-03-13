package com.tes.presentation.login

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tes.presentation.main.navigateToMain

@Composable
fun LoginScreen(navController: NavController) {
    TextButton(onClick = { navController.navigateToMain() }, content = { Text(text = "로그인") })
}
