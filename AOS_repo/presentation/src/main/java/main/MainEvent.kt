package com.tes.presentation.main

import com.tes.presentation.composebase.Event
import com.tes.presentation.composebase.SideEffect

interface MainEvent : Event

sealed class MainViewEvent : MainEvent

sealed class MainViewSideEffect : MainEvent, SideEffect {
    data class ShowToast(val message: String) : MainViewSideEffect()
}
