package com.tes.presentation.main

import com.tes.presentation.composebase.ViewState
import com.tes.presentation.main.recording.RecordingStep
import com.tes.presentation.model.Location
import com.tes.presentation.model.Vodle

sealed class MainViewState : ViewState {
    abstract val vodleMap: HashMap<Location, List<Vodle>>
    abstract val toastMessage: String?
    abstract val vodleList: List<Vodle>

    data class Default(
        override val vodleMap: HashMap<Location, List<Vodle>> = HashMap<Location, List<Vodle>>(),
        override val toastMessage: String? = null,
        override val vodleList: List<Vodle> = emptyList()
    ) : MainViewState()

    data class MakingVodle(
        override val vodleMap: HashMap<Location, List<Vodle>> = HashMap<Location,List<Vodle>>(),
        val location: Location,
        override val toastMessage: String? = null,
        val recordingStep: RecordingStep = RecordingStep.INTRODUCTION,
        override val vodleList: List<Vodle> = emptyList()
    ) : MainViewState()

    data class ShowRecordedVodle(
        override val vodleMap: HashMap<Location, List<Vodle>> = HashMap<Location, List<Vodle>>(),
        override val toastMessage: String? = null,
        override val vodleList: List<Vodle>
    ) : MainViewState()
}
