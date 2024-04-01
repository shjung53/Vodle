package com.tes.presentation.main.components

import android.content.Context
import android.view.Gravity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.ExoPlayer
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.main.MainViewState
import com.tes.presentation.model.Location
import kotlinx.coroutines.CoroutineScope
import main.components.VodleListDialogComponent

@Composable
internal fun VodleDialog(
    viewModel: MainViewModel,
    viewState: MainViewState,
    context: Context,
    myLocation: Location,
    scope: CoroutineScope,
    player: ExoPlayer,
    dataSourceFactory: DataSource.Factory
) {
    Dialog(
        onDismissRequest = {
            viewModel.onTriggerEvent(MainViewEvent.OnDismissVodleDialog)
            player.pause()
            player.stop()
        }
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
        (LocalView.current.parent as DialogWindowProvider).window.setGravity(Gravity.BOTTOM)
        Column() {
            VodleListDialogComponent(
                viewModel,
                viewState.vodleList,
                context,
                myLocation,
                scope,
                player,
                dataSourceFactory
            )
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
