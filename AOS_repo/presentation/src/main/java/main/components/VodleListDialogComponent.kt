package main.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.tes.presentation.model.Vodle
import com.tes.presentation.theme.main_coral_bright
import com.tes.presentation.theme.main_coral_darken
import com.tes.presentation.theme.vodleTypoGraphy
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun VodleListDialogComponent(
    vodleList: List<Vodle>,
    player: ExoPlayer,
    dataSourceFactory: DataSource.Factory
) {
    var index by remember { mutableStateOf(0) }
    val currentProgress = remember { Animatable(0f) }
    var isPlaying by remember { mutableStateOf(false) }

    if (isPlaying) {
        PlayVodle(isPlaying, currentProgress)
    } else {
        PlayVodle(isPlaying, currentProgress)
    }

    Box(
        modifier = Modifier.then(
            Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .background(color = Color.White)
        )
    ) {
        if (index != 0) {
            IconButton(
                onClick = {
                    index--
                    isPlaying = false
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = main_coral_darken,
                    containerColor = Color.White,
                    disabledContentColor = main_coral_darken,
                    disabledContainerColor = Color.White
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "backButton",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (index != vodleList.size - 1) {
            IconButton(
                onClick = {
                    index++
                    isPlaying = false
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = main_coral_darken,
                    containerColor = Color.White,
                    disabledContentColor = main_coral_darken,
                    disabledContainerColor = Color.White
                ),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "forwardButton",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.Center)
                .padding(start = 20.dp, top = 10.dp, end = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(
                    text = vodleList.get(index).writer,
                    textAlign = TextAlign.Start,
                    style = vodleTypoGraphy.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = vodleList.get(index).address,
                    textAlign = TextAlign.Start,
                    style = vodleTypoGraphy.titleSmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(
                    text = vodleList.get(index).date,
                    textAlign = TextAlign.Start,
                    style = vodleTypoGraphy.bodySmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = vodleList.get(index).category,
                    textAlign = TextAlign.Start,
                    style = vodleTypoGraphy.titleMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

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
                                    MediaItem.fromUri(vodleList.get(index).streamingURL)
                                )
                        Log.d("확인", vodleList.get(index).streamingURL)
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
        }
    }
}

@Composable
private fun PlayVodle(
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
