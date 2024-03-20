package com.tes.presentation.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tes.domain.usecase.user.GetNaverIdUseCase
import com.tes.domain.usecase.user.SignInNaverUseCase
import com.tes.presentation.composebase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInNaverUseCase: SignInNaverUseCase,
    private val getNaverIdUseCase: GetNaverIdUseCase
) : BaseViewModel<LoginViewState, LoginViewEvent>() {
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

    private suspend fun handleLoginViewEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.OnClickGoogleLoginButton -> TODO()
            LoginViewEvent.OnClickNaverLoginButton -> setState { onAttemptToLogin() }
            is LoginViewEvent.AttemptToFetchNaverId -> fetchUserNaverId(event.accessToken)
            is LoginViewEvent.AttemptToLogin -> TODO()
            LoginViewEvent.AttemptToNaverLogin -> TODO()
            is LoginViewEvent.OnSuccessLogin -> setState { LoginViewState.Login() }
            is LoginViewEvent.ShowDialog -> setState { showDialog(event.message) }
            is LoginViewEvent.OnClickBackButton -> setState {
                onClickBackButton(
                    event.backPressedTime
                )
            }
        }
    }

    private suspend fun fetchUserNaverId(accessToken: String) {
        getNaverIdUseCase(accessToken).fold(
            onSuccess = {
                setState { LoginViewState.Login() }
            },
            onFailure = { setState { showDialog(it.message ?: "") } }
        )
    }

    private fun LoginViewState.onAttemptToLogin(): LoginViewState {
        return when (this) {
            is LoginViewState.Default -> copy(isTryingLogin = true)
            is LoginViewState.Login -> copy(isTryingLogin = true)
        }
    }

    private fun LoginViewState.showDialog(message: String): LoginViewState {
        return when (this) {
            is LoginViewState.Default -> copy(dialogMessage = message)
            is LoginViewState.Login -> copy(dialogMessage = message)
        }
    }

    private fun LoginViewState.onClickBackButton(backPressedTime: Long): LoginViewState {
        if (checkBackPressedRightBefore(backPressedTime, currentState.lastBackPressedTime)) {
            return when (this) {
                is LoginViewState.Default -> copy(shouldExit = true)
                is LoginViewState.Login -> copy(shouldExit = true)
            }
        }

        return when (this) {
            is LoginViewState.Default -> copy(
                lastBackPressedTime = backPressedTime,
                dialogMessage = "한번 더 누르면 종료됩니다."
            )

            is LoginViewState.Login -> copy(
                lastBackPressedTime = backPressedTime,
                dialogMessage = "한번 더 누르면 종료됩니다."
            )
        }
    }

    private fun checkBackPressedRightBefore(
        backPressedTime: Long,
        lastBackPressedTime: Long
    ): Boolean {
        if (backPressedTime - lastBackPressedTime < 1000) return true
        return false
    }
}
