package com.tes.presentation.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle

@Composable
internal fun OutLinedText(
    modifier: Modifier = Modifier,
    onClickText: () -> Unit,
    text: String,
    textStyle: TextStyle,
    strokeColor: Color,
    strokeWidth: Float
) {
    Box(modifier = modifier.then(Modifier.clickable { onClickText() })) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = text,
            style = textStyle.merge(
                TextStyle(
                    color = strokeColor,
                    drawStyle = Stroke(width = strokeWidth, join = StrokeJoin.Round)
                )
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
