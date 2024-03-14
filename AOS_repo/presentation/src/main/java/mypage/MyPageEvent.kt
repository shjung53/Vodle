package com.tes.presentation.mypage

import com.tes.presentation.composebase.Event
import com.tes.presentation.composebase.SideEffect

interface MyPageEvent : Event
sealed class MyPageViewEvent : MyPageEvent

sealed class MyPageViewSideEffect : MyPageEvent, SideEffect {
    data class ShowToast(val message: String) : MyPageViewSideEffect()
}
