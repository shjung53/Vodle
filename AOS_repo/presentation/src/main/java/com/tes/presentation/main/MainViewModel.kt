package com.tes.presentation.main

import androidx.lifecycle.viewModelScope
import com.tes.domain.usecase.vodle.FetchVodlesAroundUseCase
import com.tes.domain.usecase.vodle.UploadVodleUseCase
import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.main.recording.RecordingStep
import com.tes.presentation.model.Location
import com.tes.presentation.model.Vodle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchVodlesAroundUseCase: FetchVodlesAroundUseCase,
    private val uploadVodleUseCase: UploadVodleUseCase
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
            MainViewEvent.OnCompleteVodle -> TODO()
            MainViewEvent.OnFinishToast -> setState { onFinishToast() }
            is MainViewEvent.OnClickFinishRecordingButton -> setState { finishRecording() }
            is MainViewEvent.OnClickMarker -> setState { onClickMarker(event.location) }
            MainViewEvent.OnDismissVodleDialog -> setState { onDismissVodleDialog() }
            MainViewEvent.OnClickFinishRecordingButton -> setState { finishRecording() }
            MainViewEvent.OnClickMakingVodleButton -> setState { startRecording() }
            MainViewEvent.OnClickSaveVodleButton -> {}
        }
    }

    private fun searchVodlesAround() {
        viewModelScope.launch {
            fetchVodlesAroundUseCase().fold(
                onSuccess = {
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
                            makeVodleMap(vodleList), vodleList
                        )
                    }
                },
                onFailure = {}
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

    private fun MainViewState.finishRecording(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                this.copy(recordingStep = RecordingStep.CREATE)
            }

            is MainViewState.ShowRecordedVodle -> this
        }
    }

    private fun MainViewState.saveVodle(recordingFile: File) {
        // TODO 보들 저장하는거 해야한다..
        viewModelScope.launch {
            uploadVodleUseCase(recordingFile).fold(
                onSuccess = {},
                onFailure = {}
            )
    private fun MainViewState.saveRecording(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                MainViewState.Default(this.vodleMap)
            }

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
}
