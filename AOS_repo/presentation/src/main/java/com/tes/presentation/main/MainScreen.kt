package com.tes.presentation.main

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.naver.maps.map.compose.rememberCameraPositionState
import com.tes.presentation.main.components.BottomButtonGroup
import com.tes.presentation.main.components.CalendarButton
import com.tes.presentation.main.components.CurrentLocationButton
import com.tes.presentation.main.components.SearchVodleButton
import com.tes.presentation.main.components.VodleDialog
import com.tes.presentation.main.components.VodleMapClustering
import com.tes.presentation.main.recording.CreateVodleDialog
import com.tes.presentation.main.recording.IntroDuctionDialog
import com.tes.presentation.main.recording.RecordingDialog
import com.tes.presentation.main.recording.RecordingStep

@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModel>(),
    onClickUserButton: () -> Unit
) {
    val viewState = viewModel.uiState.collectAsState().value

    val cameraPositionState = rememberCameraPositionState()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val player = ExoPlayer.Builder(LocalContext.current).build()
    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

    ObserveToastMessage(viewState = viewState, context = context, viewModel = viewModel)

    VodleMapClustering(viewModel, viewState, cameraPositionState)

    Column {
        SearchVodleButton(
            viewModel = viewModel,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        CurrentLocationButton(
            viewModel = viewModel,
            context = context,
            scope = scope,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .align(Alignment.End)
        )

        CalendarButton(
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (viewState is MainViewState.ShowRecordedVodle) {
            player.pause()
            player.stop()
            Dialog(
                onDismissRequest = {
                    viewModel.onTriggerEvent(MainViewEvent.OnDismissVodleDialog)
                }
            ) {
                (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
                (LocalView.current.parent as DialogWindowProvider).window.setGravity(Gravity.BOTTOM)
                Column {
                    VodleDialog(
                        viewState,
                        player,
                        dataSourceFactory
                    )
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }

        BottomButtonGroup(
            viewModel = viewModel,
            context = context,
            scope = scope,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClickUserButton = onClickUserButton
        )
    }

    if (viewState is MainViewState.MakingVodle) {
        when (viewState.recordingStep) {
            RecordingStep.INTRODUCTION -> IntroDuctionDialog(viewModel, viewState)
            RecordingStep.RECORDING -> RecordingDialog(viewModel)
            RecordingStep.CREATE -> CreateVodleDialog(
                viewModel,
                viewState,
                player
            )
        }
    }
}

@Composable
private fun ObserveToastMessage(
    viewState: MainViewState,
    context: Context,
    viewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewState.toastMessage) {
        if (viewState.toastMessage?.isNotEmpty() == true) {
            Toast.makeText(context, viewState.toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.onTriggerEvent(MainViewEvent.OnFinishToast)
        }
    }
}
