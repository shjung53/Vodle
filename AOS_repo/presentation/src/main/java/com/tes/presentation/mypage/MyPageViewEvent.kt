package com.tes.presentation.mypage

import com.tes.presentation.composebase.ViewEvent

sealed class MyPageViewEvent : ViewEvent {
    data class ShowToast(val message: String) : MyPageViewEvent()
}
