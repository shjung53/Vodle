package com.tes.presentation.main.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMapComposable
import com.naver.maps.map.overlay.OverlayImage
import com.tes.presentation.R
import com.tes.presentation.model.Location
import com.tes.presentation.model.lat
import com.tes.presentation.model.lng

@OptIn(ExperimentalNaverMapApi::class)
@Composable
@NaverMapComposable
internal fun VodleMarker(location: Location) {
    Marker(
        width = 40.dp,
        height = 40.dp,
        icon = OverlayImage.fromResource(R.drawable.megaphone),
        state = MarkerState(position = LatLng(location.lat, location.lng))
    )
}
