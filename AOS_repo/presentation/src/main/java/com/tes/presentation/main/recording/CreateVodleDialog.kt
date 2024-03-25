package com.tes.presentation.main.recording

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.vodleTypoGraphy
import main.components.BasicDialog
import main.components.ButtonComponent

@Composable
internal fun CreateVodleDialog(viewModel: MainViewModel) {
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
                Row {
                    Text(text = "작성자")
                    TextField(value = "", onValueChange = {})
                }

                LinearProgressIndicator(
                    progress = 1f,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                ButtonComponent(
                    buttonText = "저장하기",
                    onClick = { viewModel.onTriggerEvent(MainViewEvent.OnClickSaveVodleButton) },
                    buttonTextStyle = vodleTypoGraphy.titleMedium
                )
            }
        }
    }
}
