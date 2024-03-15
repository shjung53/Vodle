package com.tes.presentation.login

import com.tes.presentation.composebase.ViewState
import com.tes.presentation.navigation.Route

sealed class LoginViewState : ViewState {

    abstract val nextRoute: Route
    abstract val isTryingLogin: Boolean

    data class Default(
        override val nextRoute: Route = Route.LOGIN,
        val isLoading: Boolean = false,
        override val isTryingLogin: Boolean = false
    ) : LoginViewState()

    data class Login(
        override val nextRoute: Route = Route.MAIN,
        override val isTryingLogin: Boolean = false
    ) : LoginViewState()
}
