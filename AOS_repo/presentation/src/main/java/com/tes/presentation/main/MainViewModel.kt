package com.tes.presentation.main

import androidx.lifecycle.viewModelScope
import com.tes.domain.model.Gender
import com.tes.domain.usecase.vodle.ConvertRecordingUseCase
import com.tes.domain.usecase.vodle.FetchVodlesAroundUseCase
import com.tes.domain.usecase.vodle.UploadVodleUseCase
import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.main.recording.RecordingStep
import com.tes.presentation.model.AudioData
import com.tes.presentation.model.Location
import com.tes.presentation.model.Url
import com.tes.presentation.model.Vodle
import com.tes.presentation.model.VoiceType
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
            is MainViewEvent.OnClickFinishRecordingButton -> finishRecording(
                event.recordingFile,
                event.selectedVoiceType,
                event.gender
            )

            is MainViewEvent.OnClickMarker -> setState { onClickMarker(event.location) }
            MainViewEvent.OnDismissVodleDialog -> setState { onDismissVodleDialog() }
            MainViewEvent.OnClickMakingVodleButton -> setState { startRecording() }
            is MainViewEvent.OnClickSaveVodleButton -> saveVodle(event.recordingFile)
            is MainViewEvent.OnFailMakingVodle -> setState { onFailMakingVodle(event.toastMessage) }
            is MainViewEvent.OnSelectVoiceType -> setState { onSelectVoiceType(event.voiceType) }
            is MainViewEvent.OnSelectGender -> setState { onSelectGender(event.gender) }
        }
    }

    private fun MainViewState.onSelectGender(selectedGender: Gender): MainViewState =
        when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> this.copy(gender = selectedGender)
            is MainViewState.ShowRecordedVodle -> this
        }

    private fun MainViewState.onSelectVoiceType(selectedVoiceType: VoiceType): MainViewState =
        when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> this.copy(selectedVoiceType = selectedVoiceType)
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
                    onTriggerEvent(MainViewEvent.OnFailMakingVodle("보들을 가져오는데 실패했습니다."))
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

    private suspend fun convertRecording(
        recordingFile: File,
        selectedVoice: VoiceType,
        gender: Gender
    ): Result<String> =
        convertRecordingUseCase(recordingFile, selectedVoice.eng, gender).fold(
            onSuccess = {
                Result.success(it.convertedAudioUrl)
            },
            onFailure = {
                Result.failure(it)
            }
        )

    private fun finishRecording(recordingFile: File, selectedVoice: VoiceType, gender: Gender) {
        viewModelScope.launch {
            setState { setLoading() }
            convertRecording(recordingFile, selectedVoice, gender).fold(
                onSuccess = {
                    setState { onFinishConversion(selectedVoice, it) }
                },
                onFailure = { onTriggerEvent(MainViewEvent.OnFailMakingVodle("문제가 발생했습니다.")) }
            )
        }
    }

    private fun MainViewState.setLoading(): MainViewState =
        when (this) {
            is MainViewState.Default -> this.copy(isLoading = true)
            is MainViewState.MakingVodle -> this.copy(isLoading = true)
            is MainViewState.ShowRecordedVodle -> this.copy(isLoading = true)
        }

    private fun MainViewState.onFinishConversion(
        voiceType: VoiceType,
        convertedUrl: Url
    ): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                this.copy(
                    recordingStep = RecordingStep.CREATE,
                    recordingFile = recordingFile,
                    convertedAudio = AudioData(voiceType, convertedUrl),
                    isLoading = false
                )
            }

            is MainViewState.ShowRecordedVodle -> this.copy(isLoading = false)
        }
    }

    private fun saveVodle(recordingFile: File) {
        // TODO 보들 저장하는거 해야한다..
        viewModelScope.launch {
            uploadVodleUseCase(recordingFile).fold(
                onSuccess = {
                    setState { onSuccessSaveVodle() }
                },
                onFailure = { onTriggerEvent(MainViewEvent.OnFailMakingVodle("보들을 저장하는데 실패했습니다.")) }
            )
        }
    }

    private fun MainViewState.onSuccessSaveVodle(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this.copy(isLoading = false)
            is MainViewState.MakingVodle -> {
                MainViewState.Default(this.vodleMap, vodleList = this.vodleList, isLoading = false)
            }

            is MainViewState.ShowRecordedVodle -> this.copy(isLoading = false)
        }
    }

    private fun MainViewState.onFailMakingVodle(toastMessage: String): MainViewState =
        when (this) {
            is MainViewState.Default -> this.copy(isLoading = false)
            is MainViewState.MakingVodle -> MainViewState.Default(
                vodleMap,
                toastMessage,
                vodleList,
                isLoading = false
            )
            is MainViewState.ShowRecordedVodle -> this.copy(isLoading = false)
        }
}

private fun MainViewState.updateVodles(
    vodleMap: HashMap<Location, List<Vodle>>,
    vodleList: List<Vodle>
): MainViewState {
    return when (this) {
        is MainViewState.Default -> {
            this.copy(vodleMap = vodleMap, vodleList = vodleList, isLoading = false)
        }

        is MainViewState.MakingVodle -> this.copy(isLoading = false)

        is MainViewState.ShowRecordedVodle -> {
            this.copy(vodleMap = vodleMap, vodleList = vodleList, isLoading = false)
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
            location = location,
            convertedAudio = AudioData()
        )

        is MainViewState.MakingVodle -> this

        is MainViewState.ShowRecordedVodle -> MainViewState.MakingVodle(
            this.vodleMap,
            location = location,
            convertedAudio = AudioData()
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
