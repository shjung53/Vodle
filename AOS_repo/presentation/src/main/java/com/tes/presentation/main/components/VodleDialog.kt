package com.tes.presentation.main.components

import androidx.compose.runtime.Composable
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.ExoPlayer
import com.tes.presentation.main.MainViewState
import main.components.VodleListDialogComponent

@Composable
internal fun VodleDialog(
    viewState: MainViewState,
    player: ExoPlayer,
    dataSourceFactory: DataSource.Factory
) {
    VodleListDialogComponent(viewState.vodleList, player, dataSourceFactory)
}
