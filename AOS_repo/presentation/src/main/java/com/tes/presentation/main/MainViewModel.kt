package com.tes.presentation.main

import com.tes.presentation.composebase.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainViewState, MainEvent>() {
    override fun createInitialState(): MainViewState {
        TODO("Not yet implemented")
    }

    override fun onTriggerEvent(event: MainEvent) {
        TODO("Not yet implemented")
    }
}
