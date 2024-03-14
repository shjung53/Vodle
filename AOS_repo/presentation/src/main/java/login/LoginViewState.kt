package com.tes.presentation.login

import com.tes.presentation.composebase.ViewState

sealed class LoginViewState : ViewState {

    abstract val isLoading: Boolean

    data class Default(
        override val isLoading: Boolean = false
    ) : LoginViewState()
}
