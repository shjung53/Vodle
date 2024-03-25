package com.tes.presentation.main

import androidx.lifecycle.viewModelScope
import com.tes.domain.usecase.vodle.FetchVodlesAroundUseCase
import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.model.Location
import com.tes.presentation.model.Vodle
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
            is MainViewEvent.OnClickMarker -> setState { onClickMarker(event.location)}
            MainViewEvent.OnDismissVodleDialog -> setState { onDismissVodleDialog() }
        }
    }

    private fun searchVodlesAround() {
        viewModelScope.launch {
            fetchVodlesAroundUseCase().fold(
                onSuccess = {
                    setState {
                        updateVodles(
                            it.map {
                                Vodle(
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
                onFailure = {
                }
            )
        }
    }

    private fun MainViewState.updateVodles(vodleList: List<Vodle>): MainViewState {
        return when (this) {
            is MainViewState.Default -> {
                this.copy(vodleList = vodleList)
            }

            is MainViewState.MakingVodle -> this

            is MainViewState.ShowRecordedVodle -> {
                this.copy(vodleList = vodleList)
            }
        }
    }

    private fun MainViewState.onDismissDialog(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> MainViewState.Default(this.vodleList)
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
                this.vodleList,
                location = location
            )

            is MainViewState.MakingVodle -> this

            is MainViewState.ShowRecordedVodle -> MainViewState.MakingVodle(
                this.vodleList,
                location = location
            )
        }
    }

    private fun MainViewState.onClickMarker(location: Location): MainViewState{
        return when (this) {
            is MainViewState.Default -> MainViewState.ShowRecordedVodle(
                this.vodleList,
                location
            )

            is MainViewState.MakingVodle -> this

            is MainViewState.ShowRecordedVodle -> this
        }
    }

    private fun MainViewState.onDismissVodleDialog(): MainViewState {
        return when (this) {
            is MainViewState.Default -> this
            is MainViewState.MakingVodle -> MainViewState.Default(this.vodleList)
            is MainViewState.ShowRecordedVodle -> MainViewState.Default(this.vodleList)
        }
    }
}
