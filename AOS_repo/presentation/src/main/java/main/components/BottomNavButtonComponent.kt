package com.tes.presentation.main.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.tes.presentation.theme.main_coral_darken

@Composable
fun BottomNavButtonComponent(
    imageVector: ImageVector,
    flag: Boolean = true,
    onClick: () -> Unit,
    info: String?,
    modifier: Modifier
) {
    IconButton(
        onClick = {
            onClick()
        },
        enabled = flag,
        modifier = modifier,
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
