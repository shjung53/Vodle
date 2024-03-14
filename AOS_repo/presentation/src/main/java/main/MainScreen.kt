package com.tes.presentation.main

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(onClickText: () -> Unit) {
    TextButton(onClick = onClickText, content = { Text(text = "메인") })
}
