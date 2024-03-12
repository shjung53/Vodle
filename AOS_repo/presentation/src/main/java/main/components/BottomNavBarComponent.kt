package com.tes.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavBarComponent(
    flag: Boolean = true,
    onClick: () -> Unit,
) {
    Row(

    ) {
        BottomButtonComponent(imageVector = Icons.Outlined.Draw, onClick = {}, info = "보들 그리기")
        BottomButtonComponent(
            imageVector = Icons.Outlined.Headphones,
            onClick = {},
            info = "오버히어링 모드"
        )
        BottomButtonComponent(imageVector = Icons.Outlined.Draw, onClick = {}, info = "녹음하기")
        BottomButtonComponent(imageVector = Icons.Outlined.Headphones, onClick = {}, info = "마이페이지")
    }

}


@Preview
@Composable
fun PreviewBottomNavBarComponent(){
    BottomNavBarComponent {

    }
}
