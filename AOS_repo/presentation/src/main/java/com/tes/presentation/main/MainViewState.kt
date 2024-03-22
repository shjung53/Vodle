package com.tes.presentation.main

import com.tes.presentation.composebase.ViewState
import com.tes.presentation.model.Location
import com.tes.presentation.model.VodleForMap

sealed class MainViewState : ViewState {
    abstract val vodleList: List<VodleForMap>
    abstract val toastMessage: String?

    data class Default(
        override val vodleList: List<VodleForMap> = emptyList(),
        override val toastMessage: String? = null
    ) : MainViewState()

    data class MakingVodle(
        override val vodleList: List<VodleForMap> = emptyList(),
        val location: Location,
        override val toastMessage: String? = null
    ) : MainViewState()

    data class ShowRecordedVodle(
        override val vodleList: List<VodleForMap> = emptyList(),
        val location: Location,
        override val toastMessage: String? = null
    ) : MainViewState()
}
