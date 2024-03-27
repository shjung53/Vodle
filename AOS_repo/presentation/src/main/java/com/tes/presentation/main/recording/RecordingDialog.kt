package com.tes.presentation.main.recording

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tes.presentation.R
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.theme.Color
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.vodleTypoGraphy
import main.components.BasicDialog
import main.components.ButtonComponent

@Composable
internal fun RecordingDialog(viewModel: MainViewModel) {
    Dialog(
        onDismissRequest = { viewModel.onTriggerEvent(MainViewEvent.OnDismissRecordingDialog) }
    ) {
        BasicDialog {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Padding.dialogPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.megaphone),
                    modifier = Modifier.weight(1f),
                    contentDescription = null
                )

                Text(
                    text = "녹음 중입니다.\n하고싶은 말을 남겨주세요!",
                    style = vodleTypoGraphy.bodyMedium
                )

                LinearProgressIndicator(
                    progress = 1f,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Text(
                    text = "시간",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp, end = 12.dp),
                    style = vodleTypoGraphy.bodySmall.merge(
                        TextStyle(color = Color.lightGrey)
                    )
                )

                ButtonComponent(
                    modifier = Modifier.padding(top = 8.dp),
                    buttonText = "녹음 끝났어요",
                    onClick = {
                        viewModel.onTriggerEvent(MainViewEvent.OnClickFinishRecordingButton)
                    },
                    buttonTextStyle = vodleTypoGraphy.titleMedium
                )
            }
        }
    }
}
