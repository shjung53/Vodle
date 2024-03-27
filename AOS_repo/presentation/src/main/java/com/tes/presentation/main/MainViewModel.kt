package com.tes.presentation.main

import androidx.lifecycle.viewModelScope
import com.tes.domain.usecase.vodle.FetchVodlesAroundUseCase
import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.main.recording.RecordingStep
import com.tes.presentation.model.Location
import com.tes.presentation.model.VodleForMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchVodlesAroundUseCase: FetchVodlesAroundUseCase

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
            MainViewEvent.OnClickFinishRecordingButton -> setState { finishRecording() }
            MainViewEvent.OnClickMakingVodleButton -> setState { startRecording() }
            MainViewEvent.OnClickSaveVodleButton -> setState { saveRecording() }
        }
    }

    private fun searchVodlesAround() {
        viewModelScope.launch {
            fetchVodlesAroundUseCase().fold(
                onSuccess = {
                    setState {
                        updateVodles(
                            it.map {
                                VodleForMap(
                                    it.id,
                                    it.date,
                                    it.address,
                                    it.writer,
                                    it.category,
                                    it.location
                                )
                            }
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
        }
    }

    private fun MainViewState.finishRecording(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                this.copy(recordingStep = RecordingStep.CREATE)
            }
        }
    }

    private fun MainViewState.saveRecording(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> {
                MainViewState.Default(this.vodleList)
            }
        }
    }

    private fun MainViewState.updateVodles(vodleList: List<VodleForMap>): MainViewState {
        return when (this) {
            is MainViewState.Default -> {
                this.copy(vodleList = vodleList)
            }

            is MainViewState.MakingVodle -> this
        }
    }

    private fun MainViewState.onDismissDialog(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> MainViewState.Default(this.vodleList)
        }
    }

    private fun MainViewState.onFinishToast(): MainViewState {
        return when (this) {
            is MainViewState.Default -> copy(toastMessage = "")
            is MainViewState.MakingVodle -> copy(toastMessage = "")
        }
    }

    private fun MainViewState.showToast(message: String): MainViewState {
        return when (this) {
            is MainViewState.Default -> copy(toastMessage = message)
            is MainViewState.MakingVodle -> copy(toastMessage = message)
        }
    }

    private fun MainViewState.onStartRecord(location: Location): MainViewState {
        return when (this) {
            is MainViewState.Default -> MainViewState.MakingVodle(
                this.vodleList,
                location = location
            )

            is MainViewState.MakingVodle -> this
        }
    }
}
