package com.tes.presentation.main.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.tes.presentation.main.MainViewState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun VodleMap(viewState: MainViewState, cameraPositionState: CameraPositionState) {
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
    ) {
        viewState.vodleList.forEach {
            VodleMarker(location = it.location)
        }
    }
}
