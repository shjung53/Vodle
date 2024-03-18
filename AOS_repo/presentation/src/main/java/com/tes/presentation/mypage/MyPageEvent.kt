package com.tes.presentation.mypage

import com.tes.presentation.composebase.SideEffect
import com.tes.presentation.composebase.ViewEvent

interface MyPageEvent : ViewEvent
sealed class MyPageViewEvent : MyPageEvent

sealed class MyPageViewSideEffect : MyPageEvent, SideEffect {
    data class ShowToast(val message: String) : MyPageViewSideEffect()
}
