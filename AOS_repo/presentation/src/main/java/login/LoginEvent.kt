package com.tes.presentation.login

import com.tes.presentation.composebase.Event
import com.tes.presentation.composebase.SideEffect
import com.tes.presentation.navigation.Route

interface LoginEvent : Event

sealed class LoginViewEvent : LoginEvent {
    data object OnClickNaverLoginButton : LoginViewEvent()

    data object OnClickGoogleLoginButton : LoginViewEvent()
}

sealed class LoginViewSideEffect : LoginEvent, SideEffect {

    data object AttemptToNaverLogin : LoginViewSideEffect()

    data class AttemptToFetchNaverId(val accessToken: String) : LoginViewSideEffect()

    data class AttemptToLogin(val id: String) : LoginViewSideEffect()

    data class OnSuccessLogin(val route: Route) : LoginViewSideEffect()

    data class ShowSnackBar(val message: String) : LoginViewSideEffect()
}
