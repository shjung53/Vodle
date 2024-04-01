package com.tes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tes.presentation.navigation.AppNavHost
import com.tes.presentation.theme.VodleTheme
import com.tes.presentation.utils.MediaPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaPlayer.initSampleList(this)
        setContent {
            VodleTheme {
                AppNavHost()
            }
        }
    }
}
