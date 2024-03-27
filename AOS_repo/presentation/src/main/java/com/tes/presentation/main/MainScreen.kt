package com.tes.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.map.compose.rememberCameraPositionState
import com.tes.presentation.main.components.BottomButtonGroup
import com.tes.presentation.main.components.CalendarButton
import com.tes.presentation.main.components.CurrentLocationButton
import com.tes.presentation.main.components.SearchVodleButton
import com.tes.presentation.main.components.VodleMap
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

    ObserveToastMessage(viewState = viewState, context = context, viewModel = viewModel)

    VodleMap(viewState, cameraPositionState)

    Column {
        SearchVodleButton(
            viewModel = viewModel,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            cameraPositionState = cameraPositionState
        )

        CurrentLocationButton(
            viewModel = viewModel,
            scope = scope,
            context = context,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .align(Alignment.End)
        )

        CalendarButton(
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.weight(1f))

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
            RecordingStep.INTRODUCTION -> IntroDuctionDialog(viewModel)
            RecordingStep.RECORDING -> RecordingDialog(viewModel)
            RecordingStep.CREATE -> CreateVodleDialog(viewModel)
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
