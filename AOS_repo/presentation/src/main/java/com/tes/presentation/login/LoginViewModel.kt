package com.tes.presentation.login

import androidx.lifecycle.viewModelScope
import com.tes.presentation.composebase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginViewState, LoginViewEvent>() {
    override fun createInitialState(): LoginViewState =
        LoginViewState.Default()

    override fun onTriggerEvent(event: LoginViewEvent) {
        setEvent(event)
    }

    init {
        viewModelScope.launch {
            uiEvent.collect { event ->
                handleLoginViewEvent(event)
            }
        }
    }

    private fun handleLoginViewEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.OnClickGoogleLoginButton -> TODO()
            LoginViewEvent.OnClickNaverLoginButton -> setState { attemptToLogin() }
            is LoginViewEvent.AttemptToFetchNaverId -> TODO()
            is LoginViewEvent.AttemptToLogin -> TODO()
            LoginViewEvent.AttemptToNaverLogin -> TODO()
            is LoginViewEvent.OnSuccessLogin -> setState { LoginViewState.Login() }
            is LoginViewEvent.ShowSnackBar -> TODO()
        }
    }

    private fun LoginViewState.attemptToLogin(): LoginViewState {
        return when (this) {
            is LoginViewState.Default -> copy(isTryingLogin = true)
            is LoginViewState.Login -> copy(isTryingLogin = true)
        }
    }
}
