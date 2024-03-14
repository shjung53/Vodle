package com.tes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tes.presentation.navigation.AppNavHost
import com.tes.presentation.theme.VodleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VodleTheme {
                AppNavHost()
            }
        }
    }
}
