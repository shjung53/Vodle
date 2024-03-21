package com.tes.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.tes.presentation.main.components.BottomButtonGroup
import com.tes.presentation.main.components.CalendarButton
import com.tes.presentation.main.components.CurrentLocationButton
import com.tes.presentation.main.components.SearchVodleButton

@OptIn(ExperimentalNaverMapApi::class)
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

    NaverMap(
        modifier = Modifier.fillMaxSize(),
        locationSource = rememberFusedLocationSource(),
        cameraPositionState = cameraPositionState,
        contentPadding = PaddingValues(48.dp),
        properties = MapProperties(
            locationTrackingMode = LocationTrackingMode.Face
        ),
        uiSettings = MapUiSettings(
            isLocationButtonEnabled = false,
            isZoomControlEnabled = false,
            isCompassEnabled = false
        )
    )

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
        Dialog(
            onDismissRequest = { viewModel.onTriggerEvent(MainViewEvent.OnDismissRecordingDialog) }
        ) {
            Text(text = "레코딩", style = TextStyle(fontSize = 24.sp))
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
