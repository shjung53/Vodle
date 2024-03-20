package com.tes.presentation.login

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
            LoginViewEvent.OnClickNaverLoginButton -> setState { onAttemptNaverLogin() }
            is LoginViewEvent.AttemptToFetchNaverId -> fetchUserNaverId(event.accessToken)
            is LoginViewEvent.OnSuccessLogin -> setState { LoginViewState.Login() }
            is LoginViewEvent.ShowDialog -> setState { showDialog(event.message) }
            is LoginViewEvent.OnClickBackButton -> setState {
                onClickBackButton(
                    event.backPressedTime
                )
            }

            LoginViewEvent.OnFinishDialog -> setState { onFinishDialog() }
        }
    }

    private suspend fun fetchUserNaverId(accessToken: String) {
        getNaverIdUseCase(accessToken).fold(
            onSuccess = {
                tryToLogin(it)
            },
            onFailure = { setState { showDialog(it.message ?: "네이버 접속 실패") } }
        )
    }

    private fun LoginViewState.onAttemptNaverLogin(): LoginViewState {
        return when (this) {
            is LoginViewState.Default -> copy(isTryingLogin = true)
            is LoginViewState.Login -> copy(isTryingLogin = true)
        }
    }

    private suspend fun tryToLogin(naverId: String) {
        signInNaverUseCase(naverId).fold(
            onSuccess = {
                setState { LoginViewState.Login() }
            },
            onFailure = {
                setState { showDialog(it.message ?: "네트워크 에러") }
            }
        )
    }

    private fun LoginViewState.showDialog(message: String): LoginViewState {
        return when (this) {
            is LoginViewState.Default -> copy(dialogMessage = message, isTryingLogin = false)
            is LoginViewState.Login -> copy(dialogMessage = message, isTryingLogin = false)
        }
    }

    private fun LoginViewState.onFinishDialog(): LoginViewState {
        return when (this) {
            is LoginViewState.Default -> copy(dialogMessage = "", isTryingLogin = false)
            is LoginViewState.Login -> copy(dialogMessage = "", isTryingLogin = false)
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
        return backPressedTime - lastBackPressedTime < 1000
    }
}
