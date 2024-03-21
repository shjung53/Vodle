package com.tes.presentation.main

import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.model.Location
import com.tes.presentation.model.Vodle.VodleForMap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainViewState, MainViewEvent>() {
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
        }
    }

    private fun searchVodlesAround() {
        setState { updateVodles() }
    }

    private fun MainViewState.updateVodles(): MainViewState {
        return when (this) {
            is MainViewState.Default -> {
                val newList = mutableListOf<VodleForMap>()
                newList.add(
                    VodleForMap(
                        1,
                        "날짜",
                        "주소",
                        "작성자",
                        "카테고리",
                        Location(36.1071402 + Math.random(), 128.4164788 + Math.random())
                    )
                )
                this.copy(vodleList = newList)
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
