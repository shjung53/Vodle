package com.tes.presentation.login

import com.tes.presentation.composebase.ViewEvent
import com.tes.presentation.navigation.Route

sealed class LoginViewEvent : ViewEvent {
    data object OnClickNaverLoginButton : LoginViewEvent()

    data object OnClickGoogleLoginButton : LoginViewEvent()

    data object AttemptToNaverLogin : LoginViewEvent()

    data class AttemptToFetchNaverId(val accessToken: String) : LoginViewEvent()

    data class AttemptToLogin(val id: String) : LoginViewEvent()

    data class OnSuccessLogin(val route: Route) : LoginViewEvent()

    data class ShowSnackBar(val message: String) : LoginViewEvent()
}
