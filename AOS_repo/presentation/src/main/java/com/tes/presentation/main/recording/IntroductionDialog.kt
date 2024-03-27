package com.tes.presentation.main.recording

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.tes.presentation.R
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.vodleTypoGraphy
import main.components.BasicDialog
import main.components.ButtonComponent

@Composable
internal fun IntroDuctionDialog(viewModel: MainViewModel) {
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
                    painter = painterResource(id = R.drawable.logo_panpare),
                    modifier = Modifier
                        .weight(1f),
                    contentDescription = null
                )
                ButtonComponent(
                    buttonText = "보들 만들기",
                    onClick = { viewModel.onTriggerEvent(MainViewEvent.OnClickMakingVodleButton) },
                    buttonTextStyle = vodleTypoGraphy.titleMedium
                )
            }
        }
    }
}
