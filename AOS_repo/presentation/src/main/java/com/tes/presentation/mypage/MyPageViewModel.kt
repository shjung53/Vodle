package com.tes.presentation.mypage

import androidx.lifecycle.viewModelScope
import com.tes.domain.usecase.user.LogoutUseCase
import com.tes.domain.usecase.user.SignOutNaverUseCase
import com.tes.presentation.BuildConfig
import com.tes.presentation.composebase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val singOutUseCase: SignOutNaverUseCase
) : BaseViewModel<MyPageViewState, MyPageViewEvent>() {
    override fun createInitialState(): MyPageViewState = MyPageViewState.Default()

    override fun onTriggerEvent(event: MyPageViewEvent) {
        when (event) {
            MyPageViewEvent.OnClickLogout -> logout()
            MyPageViewEvent.OnClickBackButton -> TODO()
            MyPageViewEvent.OnClickPrivacyPolicy -> TODO()
            is MyPageViewEvent.OnClickSignOut -> setState { tryingSignOut() }
            MyPageViewEvent.OnClickMyVodleLog -> TODO()
            is MyPageViewEvent.ShowToast -> TODO()
            MyPageViewEvent.OnCancelSignOut -> setState { cancelSignOut() }
            is MyPageViewEvent.OnClickSignOutConfirm -> signOut(event.naverAccessToken)
        }
    }

    private fun MyPageViewState.cancelSignOut(): MyPageViewState =
        when (this) {
            is MyPageViewState.Default -> copy(isTryingSignOut = false)
            is MyPageViewState.VodleLog -> this
        }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase().fold(
                onSuccess = {
                    setState { onSuccessLogout() }
                },
                onFailure = {}
            )
        }
    }

    private fun MyPageViewState.tryingSignOut(): MyPageViewState =
        when (this) {
            is MyPageViewState.Default -> this.copy(isTryingSignOut = true)
            is MyPageViewState.VodleLog -> this
        }

    private fun signOut(accessToken: String) {
        viewModelScope.launch {
            singOutUseCase(
                BuildConfig.NAVER_LOGIN_CLIENT_ID,
                BuildConfig.NAVER_LOGIN_CLIENT_SECRET,
                accessToken
            ).fold(
                onSuccess = {
                    logout()
                },
                onFailure = {}
            )
        }
    }

    private fun MyPageViewState.onSuccessLogout(): MyPageViewState =
        when (this) {
            is MyPageViewState.Default -> this.copy(isLogin = false)
            is MyPageViewState.VodleLog -> this
        }
}
