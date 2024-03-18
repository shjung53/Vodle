package com.tes.presentation.login

import com.tes.presentation.composebase.ViewEvent

sealed class LoginViewEvent : ViewEvent {
    data object OnClickNaverLoginButton : LoginViewEvent()

    data object OnClickGoogleLoginButton : LoginViewEvent()

    data class OnClickBackButton(val backPressedTime: Long) : LoginViewEvent()

    data object AttemptToNaverLogin : LoginViewEvent()

    data class AttemptToFetchNaverId(val accessToken: String) : LoginViewEvent()

    data class AttemptToLogin(val id: String) : LoginViewEvent()

    data object OnSuccessLogin : LoginViewEvent()

    data class ShowDialog(val message: String) : LoginViewEvent()
}
