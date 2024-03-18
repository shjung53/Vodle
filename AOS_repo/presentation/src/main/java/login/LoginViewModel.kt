package com.tes.presentation.login

import androidx.lifecycle.viewModelScope
import com.tes.presentation.composebase.BaseViewModel
import com.tes.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginViewState, LoginEvent>() {

    init {
        viewModelScope.launch {
            uiEvent.collect { it ->
                when (it) {
                    is LoginViewEvent -> {
                        handleLoginViewEvent(it)
                    }

                    is LoginViewSideEffect -> {
                        handleLoginSideEffect(it)
                    }
                }
            }
        }
    }
    override fun createInitialState(): LoginViewState =
        LoginViewState.Default()

    override fun onTriggerEvent(event: LoginEvent) {
        setEvent(event)
    }

    private fun handleLoginViewEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.OnClickGoogleLoginButton -> TODO()

            LoginViewEvent.OnClickNaverLoginButton -> setEvent(
                LoginViewSideEffect.OnSuccessLogin(
                    Route.MAIN
                )
            )
        }
    }

    private fun handleLoginSideEffect(sideEffect: LoginViewSideEffect) {
        when (sideEffect) {
            is LoginViewSideEffect.AttemptToFetchNaverId -> TODO()
            is LoginViewSideEffect.AttemptToLogin -> TODO()
            LoginViewSideEffect.AttemptToNaverLogin -> TODO()
            is LoginViewSideEffect.OnSuccessLogin -> setState { LoginViewState.Login() }
            is LoginViewSideEffect.ShowSnackBar -> TODO()
        }
    }
}
