package com.tes.presentation.login

import com.tes.presentation.composebase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginViewState, LoginEvent>() {
    override fun createInitialState(): LoginViewState =
        LoginViewState.Default()

    override fun onTriggerEvent(event: LoginEvent) {
        TODO("Not yet implemented")
    }
}
