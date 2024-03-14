package com.tes.presentation.login

import com.tes.presentation.composebase.Event
import com.tes.presentation.composebase.SideEffect

interface LoginEvent : Event
sealed class LoginViewEvent : LoginEvent

sealed class LoginViewSideEffect : LoginEvent, SideEffect {

    data class ShowToast(val message: String) : LoginViewSideEffect()
}
