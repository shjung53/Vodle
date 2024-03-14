package com.tes.presentation.main

import com.tes.presentation.composebase.ViewState

sealed class MainViewState : ViewState {
    abstract val isLoading: Boolean

    data class Default(
        override val isLoading: Boolean = false
    ) : MainViewState()
}
