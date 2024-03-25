package com.tes.presentation.main.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tes.presentation.model.Vodle
import main.components.VodleListDialogComponent

@Composable
internal fun VodleDialog(
    vodleList: List<Vodle>,
    modifier: Modifier
) {
    VodleListDialogComponent(vodleList, {})
}
