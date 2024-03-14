package com.tes.presentation.main

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tes.presentation.mypage.navigateToMyPage

@Composable
fun MainScreen(navController: NavController) {
    TextButton(onClick = { navController.navigateToMyPage() }, content = { Text(text = "메인") })
}
