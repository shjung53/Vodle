package com.tes.presentation.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.MicNone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tes.presentation.theme.main_coral_darken

@Composable
fun BottomNavBarComponent(
    flag: Boolean = true,
    clickDraw: () -> Unit,
    clickOverhearing: () -> Unit,
    clickRecord: () -> Unit,
    clickMypage: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(0.88f)
            .border(BorderStroke(1.dp, color = main_coral_darken), shape = RoundedCornerShape(20))
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BottomNavButtonComponent(
                imageVector = Icons.Outlined.Draw,
                flag = flag,
                onClick = clickDraw,
                info = "보들 그리기",
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )
            BottomNavButtonComponent(
                imageVector = Icons.Outlined.Headphones,
                flag = flag,
                onClick = clickOverhearing,
                info = "오버히어링 모드", modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )
            BottomNavButtonComponent(
                imageVector = Icons.Outlined.MicNone,
                flag = flag,
                onClick = clickRecord,
                info = "녹음하기",
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )
            BottomNavButtonComponent(
                imageVector = Icons.Outlined.Person,
                flag = flag,
                onClick = clickMypage,
                info = "마이페이지",
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavBarComponent() {
    BottomNavBarComponent(
        clickDraw = {},
        clickOverhearing = {},
        clickRecord = {},
        clickMypage = {}
    )
}
