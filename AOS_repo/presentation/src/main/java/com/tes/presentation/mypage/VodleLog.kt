package com.tes.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.tes.presentation.R
import com.tes.presentation.model.VodleForLog
import com.tes.presentation.theme.main_coral
import com.tes.presentation.theme.vodleTypoGraphy

@Composable
fun VodleLog(vodleForLog: VodleForLog) {
    Box {
        Image(painterResource(id = R.drawable.back_spray), contentDescription = null)
        CustomStyledText(address = vodleForLog.address)
        Text(text = vodleForLog.date)
    }
}

@Composable
fun CustomStyledText(address: String) {
    val text = buildAnnotatedString {
        withStyle(style = vodleTypoGraphy.titleMedium.toSpanStyle()) {
            append(address + "에 남긴 ")
        }

        withStyle(style = vodleTypoGraphy.titleMedium.merge(color = main_coral).toSpanStyle()) {
            append("보들")
        }
    }

    Text(text = text)
}
