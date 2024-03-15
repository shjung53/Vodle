package com.tes.presentation.main

import com.tes.presentation.composebase.SideEffect
import com.tes.presentation.composebase.ViewEvent

interface MainEvent : ViewEvent

sealed class MainViewEvent : MainEvent

sealed class MainViewSideEffect : MainEvent, SideEffect {
    data class ShowToast(val message: String) : MainViewSideEffect()
}
