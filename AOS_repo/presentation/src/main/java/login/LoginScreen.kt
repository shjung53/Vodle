package com.tes.presentation.login

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(onClickText: () -> Unit) {
    TextButton(onClick = onClickText, content = { Text(text = "로그인") })
}
