package com.tes.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.tes.presentation.R

@Composable
internal fun VodleDialog(){
    Column {
        Row {
            Text(text = "작성자")
            Text(text = "날짜")
            Text(text = "주소")
        }
        Row {
            Image(painter= painterResource(id = R.drawable.left_arrow), null)
            Text(text = "카테고리")
            Image(painter= painterResource(id = R.drawable.right_arrow), null)
        }
        Row {
            LinearProgressIndicator()
        }
    }
}
