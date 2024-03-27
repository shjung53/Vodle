package com.tes.presentation.main.recording

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.vodleTypoGraphy
import main.components.ButtonComponent

@Composable
internal fun CreateVodleDialog(viewModel: MainViewModel) {
    Dialog(
        onDismissRequest = { viewModel.onTriggerEvent(MainViewEvent.OnDismissRecordingDialog) }
    ) {
        val writer = remember { mutableStateOf("") }
        val isPlaying = remember {
            mutableStateOf(false)
        }

        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(shape = RoundedCornerShape(32.dp))
                .wrapContentHeight()
                .background(Color.White)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(Padding.dialogPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WriterRow(writer = writer)

                Spacer(modifier = Modifier.height(32.dp))

                VoiceSelector()

                Spacer(modifier = Modifier.height(32.dp))

                Row() {
                    if (isPlaying.value) {

                    } else {

                    }
                    LinearProgressIndicator(
                        progress = 1f,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                ButtonComponent(
                    buttonText = "저장하기",
                    onClick = { viewModel.onTriggerEvent(MainViewEvent.OnClickSaveVodleButton) },
                    buttonTextStyle = vodleTypoGraphy.titleMedium
                )
            }
        }
    }
}
