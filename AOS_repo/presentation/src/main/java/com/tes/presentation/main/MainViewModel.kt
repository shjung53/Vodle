package com.tes.presentation.main

import androidx.lifecycle.viewModelScope
import com.tes.domain.usecase.vodle.ConvertRecordingUseCase
import com.tes.domain.usecase.vodle.FetchVodlesAroundUseCase
import com.tes.domain.usecase.vodle.UploadVodleUseCase
import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.main.recording.AudioData
import com.tes.presentation.main.recording.RecordingStep
import com.tes.presentation.main.recording.VoiceType
import com.tes.presentation.model.Location
import com.tes.presentation.model.Vodle
import com.tes.presentation.utils.AudioDataString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchVodlesAroundUseCase: FetchVodlesAroundUseCase,
    private val uploadVodleUseCase: UploadVodleUseCase,
    private val convertRecordingUseCase: ConvertRecordingUseCase
) : BaseViewModel<MainViewState, MainViewEvent>() {
    override fun createInitialState(): MainViewState =
        MainViewState.Default()

    override fun onTriggerEvent(event: MainViewEvent) {
        when (event) {
            MainViewEvent.OnClickHeadPhoneButton -> TODO()
            is MainViewEvent.OnClickRecordingButton -> setState { onStartRecord(event.location) }
            is MainViewEvent.OnClickSearchVodleButton -> searchVodlesAround()
            MainViewEvent.OnClickUserButton -> TODO()
            MainViewEvent.OnClickWriteButton -> TODO()
            is MainViewEvent.ShowToast -> setState { showToast(event.message) }
            MainViewEvent.OnDismissRecordingDialog -> setState { onDismissDialog() }
            MainViewEvent.OnFinishToast -> setState { onFinishToast() }
            is MainViewEvent.OnClickFinishRecordingButton -> finishRecording(event.recordingFile)
            is MainViewEvent.OnClickMarker -> setState { onClickMarker(event.location) }
            MainViewEvent.OnDismissVodleDialog -> setState { onDismissVodleDialog() }
            MainViewEvent.OnClickMakingVodleButton -> setState { startRecording() }
            is MainViewEvent.OnClickSaveVodleButton -> saveVodle(event.recordingFile)
            is MainViewEvent.OnFinishMakeConvertedFile -> setState {
                onFinishMakeCnovertedFile(
                    event.audioFileList
                )
            }
            is MainViewEvent.OnFialMakingVodle -> setState { onFailMakingVodle(event.toastMessage) }
        }
    }

    private fun MainViewState.onFinishMakeCnovertedFile(audioFileList: List<File>): MainViewState =
        when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> this.copy(convertedAudioList = audioFileList)
            is MainViewState.ShowRecordedVodle -> this
        }

    private fun searchVodlesAround() {
        viewModelScope.launch {
            fetchVodlesAroundUseCase().fold(
                onSuccess = { it ->
                    val vodleList = it.map {
                        Vodle(
                            it.id,
                            it.date,
                            it.address,
                            it.writer,
                            it.category,
                            it.location,
                            it.streamingURL
                        )
                    }

                    setState {
                        updateVodles(
                            makeVodleMap(vodleList),
                            vodleList
                        )
                    }
                },
                onFailure = {
                    onTriggerEvent(MainViewEvent.OnFialMakingVodle("보들을 가져오는데 실패했습니다."))
                }
            )
        }
    }

    private fun MainViewState.startRecording(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                this.copy(recordingStep = RecordingStep.RECORDING)
            }

            is MainViewState.ShowRecordedVodle -> this
        }
    }

    private suspend fun convertRecording(recordingFile: File): Result<AudioDataString> =
        convertRecordingUseCase(recordingFile).fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = {
                Result.failure(it)
            }
        )

    private fun finishRecording(recordingFile: File) {
        viewModelScope.launch {
            convertRecording(recordingFile).fold(
                onSuccess = {
                    setState { onFinishConversion(recordingFile, it) }
                },
                onFailure = { onTriggerEvent(MainViewEvent.OnFialMakingVodle("문제가 발생했습니다.")) }
            )
        }
    }

    private fun MainViewState.onFinishConversion(
        recordingFile: File,
        convertedAudioDataString: AudioDataString
    ): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                val newAudioFileList = mutableListOf<File>()
                newAudioFileList.add(recordingFile)
                val audioDataList = mutableListOf<AudioData>()
                audioDataList.add(AudioData(VoiceType.ORIGINAL, ""))
                audioDataList.add(AudioData(VoiceType.MUNDO, convertedAudioDataString))
                this.copy(
                    recordingStep = RecordingStep.CREATE,
                    recordingFile = recordingFile,
                    convertedAudioList = newAudioFileList,
                    audioDataList = audioDataList
                )
            }

            is MainViewState.ShowRecordedVodle -> this
        }
    }

    private fun saveVodle(recordingFile: File) {
        // TODO 보들 저장하는거 해야한다..
        viewModelScope.launch {
            uploadVodleUseCase(recordingFile).fold(
                onSuccess = {
                    setState { onSuccessSaveVodle() }
                },
                onFailure = { onTriggerEvent(MainViewEvent.OnFialMakingVodle("보들을 저장하는데 실패했습니다.")) }
            )
        }
    }

    private fun MainViewState.onSuccessSaveVodle(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                MainViewState.Default(this.vodleMap, vodleList = this.vodleList)
            }

            is MainViewState.ShowRecordedVodle -> this
        }
    }

    private fun MainViewState.onFailMakingVodle(toastMessage: String): MainViewState =
        when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> MainViewState.Default(vodleMap, toastMessage, vodleList)
            is MainViewState.ShowRecordedVodle -> this
        }
}

private fun MainViewState.updateVodles(
    vodleMap: HashMap<Location, List<Vodle>>,
    vodleList: List<Vodle>
): MainViewState {
    return when (this) {
        is MainViewState.Default -> {
            this.copy(vodleMap = vodleMap, vodleList = vodleList)
        }

        is MainViewState.MakingVodle -> this

        is MainViewState.ShowRecordedVodle -> {
            this.copy(vodleMap = vodleMap, vodleList = vodleList)
        }
    }
}

private fun MainViewState.onDismissDialog(): MainViewState {
    return when (this) {
        is MainViewState.Default -> this
        is MainViewState.MakingVodle -> MainViewState.Default(this.vodleMap)
        is MainViewState.ShowRecordedVodle -> this
    }
}

private fun MainViewState.onFinishToast(): MainViewState {
    return when (this) {
        is MainViewState.Default -> copy(toastMessage = "")
        is MainViewState.MakingVodle -> copy(toastMessage = "")
        is MainViewState.ShowRecordedVodle -> copy(toastMessage = "")
    }
}

private fun MainViewState.showToast(message: String): MainViewState {
    return when (this) {
        is MainViewState.Default -> copy(toastMessage = message)
        is MainViewState.MakingVodle -> copy(toastMessage = message)
        is MainViewState.ShowRecordedVodle -> copy(toastMessage = message)
    }
}

private fun MainViewState.onStartRecord(location: Location): MainViewState {
    return when (this) {
        is MainViewState.Default -> MainViewState.MakingVodle(
            this.vodleMap,
            location = location
        )

        is MainViewState.MakingVodle -> this

        is MainViewState.ShowRecordedVodle -> MainViewState.MakingVodle(
            this.vodleMap,
            location = location
        )
    }
}

private fun MainViewState.onClickMarker(location: Location): MainViewState {
    return when (this) {
        is MainViewState.Default -> MainViewState.ShowRecordedVodle(
            this.vodleMap,
            "",
            this.vodleMap.get(location)!!
        )

        is MainViewState.MakingVodle -> this

        is MainViewState.ShowRecordedVodle -> this
    }
}

private fun MainViewState.onDismissVodleDialog(): MainViewState {
    return when (this) {
        is MainViewState.Default -> this
        is MainViewState.MakingVodle -> MainViewState.Default(this.vodleMap)
        is MainViewState.ShowRecordedVodle -> MainViewState.Default(
            this.vodleMap,
            "",
            this.vodleList
        )
    }
}

private fun makeVodleMap(vodleList: List<Vodle>): HashMap<Location, List<Vodle>> {
    val vodelMap: HashMap<Location, List<Vodle>> = HashMap<Location, List<Vodle>>()

    vodleList.forEach {
        val newList: MutableList<Vodle> = mutableListOf()
        val oldList: List<Vodle> = vodelMap.getOrDefault(it.location, mutableListOf())
        newList.addAll(oldList)
        newList.add(it)
        vodelMap[it.location] = newList.toList()
    }

    return vodelMap
}
