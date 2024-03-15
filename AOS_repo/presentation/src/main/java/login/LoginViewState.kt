package com.tes.presentation.login

import com.tes.presentation.composebase.ViewState
import com.tes.presentation.navigation.Route

sealed class LoginViewState : ViewState {

    abstract val nextRoute: Route

    data class Default(
        override val nextRoute: Route = Route.LOGIN,
        val isLoading: Boolean = false
    ) : LoginViewState()

    data class Login(
        override val nextRoute: Route = Route.MAIN
    ) : LoginViewState()
}
