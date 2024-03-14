package com.tes.presentation.login

import com.tes.presentation.composebase.Event
import com.tes.presentation.composebase.SideEffect
import com.tes.presentation.navigation.Route

interface LoginEvent : Event

sealed class LoginViewEvent : LoginEvent

sealed class LoginViewSideEffect : LoginEvent, SideEffect {

    data object OnClickNaverLoginButton : LoginViewSideEffect()

    data object OnClickGoogleLoginButton : LoginViewSideEffect()

    data class OnSuccessLogin(val route: Route) : LoginViewSideEffect()

    data class ShowSnackBar(val message: String) : LoginViewSideEffect()
}
