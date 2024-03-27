package com.tes.presentation.main

import com.tes.presentation.composebase.ViewState
import com.tes.presentation.main.recording.RecordingStep
import com.tes.presentation.model.Location
import com.tes.presentation.model.Vodle

sealed class MainViewState : ViewState {
    abstract val vodleList: List<Vodle>
    abstract val toastMessage: String?

    data class Default(
        override val vodleList: List<Vodle> = emptyList(),
        override val toastMessage: String? = null,
        val location: Location? = null
    ) : MainViewState()

    data class MakingVodle(
        override val vodleList: List<Vodle> = emptyList(),
        val location: Location,
        override val toastMessage: String? = null,
        val recordingStep: RecordingStep = RecordingStep.INTRODUCTION
    ) : MainViewState()

    data class ShowRecordedVodle(
        override val vodleList: List<Vodle> = emptyList(),
        override val toastMessage: String? = null,
        val location: Location? = null
    ) : MainViewState()
}
