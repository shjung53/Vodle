package main.components

import android.util.Log
import androidx.annotation.OptIn
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
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import com.tes.presentation.BuildConfig
import com.tes.presentation.model.Vodle
import com.tes.presentation.theme.main_coral_bright
import com.tes.presentation.theme.main_coral_darken
import com.tes.presentation.theme.vodleTypoGraphy
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "VodleListDialogComponen_싸피"

@OptIn(UnstableApi::class)
@Composable
fun VodleListDialogComponent(
    vodleList: List<Vodle>,
    player: ExoPlayer,
    dataSourceFactory: DataSource.Factory,
    onClick: () -> Unit,
) {
    var index by remember { mutableStateOf(0) }
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

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
                    index--;
                    loading = false
                    currentProgress = 0f
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
            Log.d(TAG, "VodleListDialogComponent: index : $index")
            IconButton(
                onClick = {
                    index++
                    loading = false
                    currentProgress = 0f
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
                        loading = !loading
                        if (loading) {
                            scope.launch {
                                loadProgress { progress ->
                                    currentProgress = progress
                                }
                                loading = false
                            }
                        }
                        else{
                            scope.cancel()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = main_coral_darken,
                        containerColor = Color.White,
                        disabledContentColor = main_coral_darken,
                        disabledContainerColor = Color.White
                    ),
                ) {
                    if (loading) {
                        var hlsMediaSource =
                            HlsMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(MediaItem.fromUri(BuildConfig.S3_URL + "2024_03_25_175598_%EC%9D%8C%EC%84%B1%ED%85%8C%EC%8A%A4%ED%8A%B8%EC%98%A4.m3u8"))
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
                    progress = { currentProgress },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    trackColor = main_coral_bright,
                    color = main_coral_darken
                )
            }
        }
    }
}

suspend fun loadProgress(
    updateProgress: (Float) -> Unit,
) {
    for (i in 1..16) {
        updateProgress(i.toFloat() / 16)
        delay(1000)
    }
}
