package com.tes.presentation.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.MicNone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tes.presentation.theme.main_coral
import com.tes.presentation.theme.main_coral_darken

@Composable
fun BottomButtonComponent(
    imageVector: ImageVector,
    flag: Boolean = true,
    onClick: () -> Unit,
    info: String,
    modifier: Modifier? = Modifier
) {
    IconButton(
        onClick = {
            onClick()
        },
        enabled = flag,
        modifier = Modifier
            .fillMaxWidth(0.25f)
            .height(50.dp),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = main_coral_darken,
            containerColor = Color.White,
            disabledContentColor = main_coral_darken,
            disabledContainerColor = Color.White
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = info,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun PreviewBottomButtonComponent() {
    Column() {
        BottomButtonComponent(imageVector = Icons.Outlined.Draw, onClick = {}, info = "그리기")
        Spacer(modifier = Modifier.height(20.dp))
        BottomButtonComponent(imageVector = Icons.Outlined.MicNone, onClick = {}, info = "녹음")
        Spacer(modifier = Modifier.height(20.dp))
        BottomButtonComponent(
            imageVector = Icons.Outlined.Headphones,
            onClick = {},
            info = "오버히어링 모드"
        )
        Spacer(modifier = Modifier.height(20.dp))
        BottomButtonComponent(imageVector = Icons.Outlined.Person, onClick = {}, info = "마이페이지")
    }
}
