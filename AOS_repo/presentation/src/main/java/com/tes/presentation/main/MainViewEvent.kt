package com.tes.presentation.main

import com.tes.presentation.composebase.ViewEvent
import com.tes.presentation.model.Location
import java.io.File

sealed class MainViewEvent : ViewEvent {

    data class OnClickSearchVodleButton(val location: Location, val radius: Long) : MainViewEvent()

    data object OnClickWriteButton : MainViewEvent()

    data object OnClickHeadPhoneButton : MainViewEvent()

    data class OnClickRecordingButton(val location: Location) : MainViewEvent()

    data object OnClickUserButton : MainViewEvent()

    data object OnDismissRecordingDialog : MainViewEvent()

    data object OnClickMakingVodleButton : MainViewEvent()
    data class OnClickFinishRecordingButton(val recordingFile: File) : MainViewEvent()
    data object OnClickSaveVodleButton : MainViewEvent()

    data object OnCompleteVodle : MainViewEvent()
    data class ShowToast(val message: String) : MainViewEvent()

    data object OnFinishToast : MainViewEvent()
}
