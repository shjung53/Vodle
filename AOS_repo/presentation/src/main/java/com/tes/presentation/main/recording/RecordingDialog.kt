package com.tes.presentation.main.recording

import android.content.Context
import android.media.MediaRecorder
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tes.presentation.R
import com.tes.presentation.main.MainViewEvent
import com.tes.presentation.main.MainViewModel
import com.tes.presentation.theme.Padding
import com.tes.presentation.theme.lightGrey
import com.tes.presentation.theme.vodleTypoGraphy
import kotlinx.coroutines.launch
import main.components.BasicDialog
import main.components.ButtonComponent
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun RecordingDialog(viewModel: MainViewModel) {
    Dialog(
        onDismissRequest = { viewModel.onTriggerEvent(MainViewEvent.OnDismissRecordingDialog) }
    ) {
        val context = LocalContext.current
        val progress = remember { Animatable(0f) }
        val isRecording = remember { mutableStateOf(true) }
        val recordingFile = createAudioFile(context)
        val recorder = MediaRecorder()

        BasicDialog {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Padding.dialogPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.megaphone),
                    modifier = Modifier.weight(1f),
                    contentDescription = null
                )

                Text(
                    text = "녹음 중입니다.\n하고싶은 말을 남겨주세요!",
                    style = vodleTypoGraphy.bodyMedium
                )

                LinearProgressIndicator(
                    progress = progress.value,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Text(
                    text = "${(progress.value * 15).toInt()} / 15초",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp, end = 12.dp),
                    style = vodleTypoGraphy.bodySmall.merge(
                        TextStyle(color = lightGrey)
                    )
                )

                ButtonComponent(
                    modifier = Modifier.padding(top = 8.dp),
                    buttonText = "녹음 끝났어요",
                    onClick = {
                        recorder.stopRecording(isRecording)
                        viewModel.onTriggerEvent(
                            MainViewEvent.OnClickFinishRecordingButton(
                                recordingFile
                            )
                        )
                    },
                    buttonTextStyle = vodleTypoGraphy.titleMedium
                )
            }
        }

        if (isRecording.value) {
            StartRecording(recordingFile, recorder, progress, isRecording)
        }
    }
}

@Composable
private fun StartRecording(
    audioFile: File,
    recorder: MediaRecorder,
    progress: Animatable<Float, AnimationVector1D>,
    isRecording: MutableState<Boolean>
) {
    val coroutineScope = rememberCoroutineScope()

    recorder.apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        setOutputFile(audioFile.absolutePath)
        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        prepare()

        start()
    }

    LaunchedEffect(isRecording) {
        val prgressAnimation = coroutineScope.launch {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 15000,
                    easing = LinearEasing
                )
            )
            recorder.stopRecording(isRecording)
        }

        if (!isRecording.value) {
            prgressAnimation.cancel()
            recorder.stopRecording(isRecording)
        }
    }
}

private fun MediaRecorder.stopRecording(isRecording: MutableState<Boolean>) {
    if (isRecording.value) {
        isRecording.value = false
        this.stop()
        this.release()
    }
}

private fun createAudioFile(context: Context): File {
    val timeStamp: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val audioFileName = "AUDIO_$timeStamp"
    val cacheDir: File = context.cacheDir
    return File.createTempFile(audioFileName, ".m4a", cacheDir)
}