package com.tes.presentation.main.recording

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.main_coral_bright
import com.tes.presentation.theme.vodleTypoGraphy

@Composable
internal fun WriterRow(writer: MutableState<String>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(start = Padding.dialogPadding),
            text = "작성자",
            style = vodleTypoGraphy.bodyMedium.merge(
                TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
        )
        WriterTextField(writer = writer, modifier = Modifier.weight(1f))
    }
}

@Composable
internal fun WriterTextField(writer: MutableState<String>, modifier: Modifier = Modifier) {
    val focusManager: FocusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier.then(
            Modifier
                .wrapContentHeight()
                .padding(horizontal = Padding.dialogPadding)
                .clip(shape = RoundedCornerShape(24.dp))
                .background(main_coral_bright)
        ),
        value = writer.value,
        onValueChange = { newText ->
            if (newText.length <= 10) {
                writer.value = newText
            }
        },
        textStyle = vodleTypoGraphy.bodyMedium.merge(
            TextStyle(
                fontSize = 16.sp
            )
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.padding(vertical = 4.dp), contentAlignment = Alignment.Center) {
                innerTextField()
            }
        }
    )
}
