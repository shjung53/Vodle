package com.tes.presentation.mypage

import com.tes.presentation.composebase.ViewState
import com.tes.presentation.model.VodleForLog

sealed class MyPageViewState : ViewState {
    abstract val isLogin: Boolean
    abstract val isTryingSignOut: Boolean

    data class Default(
        override val isLogin: Boolean = true,
        override val isTryingSignOut: Boolean = false
    ) :
        MyPageViewState()

    data class VodleLog(
        val vodleLogList: List<VodleForLog>,
        override val isLogin: Boolean = true,
        override val isTryingSignOut: Boolean = false
    ) :
        MyPageViewState()
}
