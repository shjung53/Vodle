package com.tes.presentation.mypage

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun MyPageScreen(onClickText: () -> Unit) {
    TextButton(onClick = onClickText, content = { Text(text = "마이페이지") })
}
