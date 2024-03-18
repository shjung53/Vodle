package com.tes.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tes.presentation.theme.main_red

@Composable
fun FabComponent(
    imageVector: ImageVector,
    flag: Boolean = true,
    onClick: () -> Unit,
    info: String?,
){
    IconButton(
        onClick = {
            onClick()
        },
        enabled = flag,
        modifier = Modifier.clip(CircleShape).size(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = main_red,
            containerColor = Color.White,
            disabledContentColor = main_red,
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
fun PreviewFabComponent(){
    Row {
        FabComponent(
            imageVector = Icons.Outlined.MyLocation,
            onClick = {},
            info = "내 위치로 이동",
        )
        Spacer(modifier = Modifier.width(20.dp))
        FabComponent(
            imageVector = Icons.Outlined.CalendarMonth,
            onClick = {},
            info = "날짜 이동",
        )
    }
}
