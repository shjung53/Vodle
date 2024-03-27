package com.tes.presentation.main.components

import androidx.compose.runtime.Composable
import com.naver.maps.map.compose.CameraPositionState
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.main.MainViewState
import com.tes.presentation.model.Vodle

@Composable
internal fun VodleMapClustering(
    viewModel: MainViewModel,
    viewState: MainViewState,
    cameraPositionState: CameraPositionState
) {
    VodleMapClustering(viewModel, viewState.vodleList, cameraPositionState)
}

@Composable
private fun VodleMapClustering(
    viewModel: MainViewModel,
    vodleList: List<Vodle>,
    cameraPositionState: CameraPositionState
) {
    VodleMap(viewModel, vodleList, cameraPositionState)
}
