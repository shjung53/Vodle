package com.tes.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.tes.presentation.main.components.BottomNavBarComponent
import main.components.ButtonComponent
import main.components.FabComponent

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MainScreen(onClickUserButton: () -> Unit) {
    NaverMap(
        modifier = Modifier.fillMaxSize(),
        locationSource = rememberFusedLocationSource(),
        properties = MapProperties(
            locationTrackingMode = LocationTrackingMode.Face
        ),
        uiSettings = MapUiSettings(
            isLocationButtonEnabled = false
        )
    )
    ButtonsColumn(onClickUserButton)
}

@Composable
private fun ButtonsColumn(onClickUserButton: () -> Unit) {
    Column {
        ButtonComponent(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp),
            textModifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            buttonText = "현재 위치에서 검색하기",
            onClick = { /*TODO*/ },
            buttonTextStyle = TextStyle()
        )

        FabComponent(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 12.dp, end = 12.dp),
            imageVector = Icons.Outlined.MyLocation,
            onClick = { /*TODO*/ },
            info = ""
        )

        FabComponent(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 12.dp, end = 12.dp),
            imageVector = Icons.Outlined.CalendarMonth,
            onClick = { /*TODO*/ },
            info = ""
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarComponent(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp),
            onClickWriteButton = { /*TODO*/ },
            onClickHeadphoneButton = { /*TODO*/ },
            onClickRecordButton = { /*TODO*/ },
            onClickUserButton = { onClickUserButton() }
        )
    }
}
