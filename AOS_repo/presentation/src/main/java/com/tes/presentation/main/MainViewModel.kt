package com.tes.presentation.main

import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.model.Location
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
            is MainViewEvent.OnClickSearchVodleButton -> TODO()
            MainViewEvent.OnClickUserButton -> TODO()
            MainViewEvent.OnClickWriteButton -> TODO()
            is MainViewEvent.ShowToast -> setState { showToast(event.message) }
            MainViewEvent.OnDismissRecordingDialog -> setState { onDisMissDialog() }
            MainViewEvent.OnCompleteVodle -> TODO()
            MainViewEvent.OnFinishToast -> setState { onFinishToast() }
        }
    }

    private fun MainViewState.onDisMissDialog(): MainViewState {
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
