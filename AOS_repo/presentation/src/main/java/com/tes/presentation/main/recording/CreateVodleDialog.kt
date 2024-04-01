package com.tes.presentation.main.recording

import androidx.annotation.OptIn
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.main.MainViewState
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.main_coral_bright
import com.tes.presentation.theme.main_coral_darken
import com.tes.presentation.theme.vodleTypoGraphy
import kotlinx.coroutines.launch
import main.components.ButtonComponent

@OptIn(UnstableApi::class)
@Composable
internal fun CreateVodleDialog(
    viewModel: MainViewModel,
    viewState: MainViewState.MakingVodle,
    player: ExoPlayer
) {
    Dialog(
        onDismissRequest = { viewModel.onTriggerEvent(MainViewEvent.OnDismissRecordingDialog) }
    ) {
        val writer = remember { mutableStateOf("") }
        var isPlaying by remember { mutableStateOf(false) }
        val currentProgress = remember { Animatable(0f) }
        val context = LocalContext.current
        val dataSourceFactory = DefaultDataSource.Factory(context)

        PlayRecording(isPlaying, currentProgress)

        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(shape = RoundedCornerShape(32.dp))
                .wrapContentHeight()
                .background(Color.White)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(Padding.dialogPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WriterRow(writer = writer)

                Spacer(modifier = Modifier.height(32.dp))

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    IconButton(
                        onClick = {
                            isPlaying = !isPlaying
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = main_coral_darken,
                            containerColor = Color.White,
                            disabledContentColor = main_coral_darken,
                            disabledContainerColor = Color.White
                        )
                    ) {
                        if (isPlaying) {
                            val hlsMediaSource =
                                HlsMediaSource.Factory(dataSourceFactory)
                                    .createMediaSource(
                                        MediaItem.fromUri(
                                            viewState.convertedAudio.convertedAudioUrl
                                        )
                                    )
                            Log.d("확인", viewState.convertedAudio.convertedAudioUrl)
                            player.setMediaSource(hlsMediaSource)
                            player.prepare()
                            player.play()
                            Icon(
                                imageVector = Icons.Default.Stop,
                                contentDescription = "stopButton",
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            player.pause()
                            player.stop()
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "playButton",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    LinearProgressIndicator(
                        progress = { currentProgress.value },
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = main_coral_darken,
                        trackColor = main_coral_bright
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                ButtonComponent(
                    buttonText = "저장하기",
                    onClick = {
                        viewModel.onTriggerEvent(
                            MainViewEvent.OnClickSaveVodleButton(viewState.recordingFile)
                        )
                    },
                    buttonTextStyle = vodleTypoGraphy.titleMedium
                )
            }
        }
    }
}

@Composable
private fun PlayRecording(
    isPlaying: Boolean,
    progress: Animatable<Float, AnimationVector1D>
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // isPlaying이 true일 때 애니메이션 시작
            coroutineScope.launch {
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 15000,
                        easing = LinearEasing
                    )
                )
            }
        } else {
            // isPlaying이 false일 때 애니메이션 중지 및 초기화
            progress.snapTo(0f)
        }
    }
}
