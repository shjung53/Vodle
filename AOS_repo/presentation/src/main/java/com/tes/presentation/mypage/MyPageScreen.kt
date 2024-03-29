package com.tes.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.tes.presentation.R
import com.tes.presentation.theme.main_coral_bright
import com.tes.presentation.theme.vodleTypoGraphy

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    onClickBackButton: () -> Unit,
    onSuccessLogout: () -> Unit
) {
    val viewState = viewModel.uiState.collectAsState().value
    LaunchedEffect(viewState.isLogin) {
        if (!viewState.isLogin) onSuccessLogout()
    }

    if (viewState.isTryingSignOut) {
        SignOutDialog(
            {
                viewModel.onTriggerEvent(
                    MyPageViewEvent.OnClickSignOutConfirm(fetchNaverAccessToken())
                )
            },
            { viewModel.onTriggerEvent(MyPageViewEvent.OnCancelSignOut) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(main_coral_bright)
            .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        TopBar(onClickBackButton)

        OutLinedText(
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
            onClickText = { viewModel.onTriggerEvent(MyPageViewEvent.OnClickMyVodleLog) },
            text = "내 낙서기록",
            textStyle = vodleTypoGraphy.bodyMedium.merge(TextStyle(fontSize = 24.sp)),
            strokeColor = Color.White,
            strokeWidth = 1f
        )
        OutLinedText(
            modifier = Modifier.padding(vertical = 12.dp),
            onClickText = { viewModel.onTriggerEvent(MyPageViewEvent.OnClickPrivacyPolicy) },
            text = "개인정보처리 방침",
            textStyle = vodleTypoGraphy.bodyMedium.merge(TextStyle(fontSize = 24.sp)),
            strokeColor = Color.White,
            strokeWidth = 1f
        )
        OutLinedText(
            modifier = Modifier.padding(vertical = 12.dp),
            onClickText = { viewModel.onTriggerEvent(MyPageViewEvent.OnClickLogout) },
            text = "로그아웃",
            textStyle = vodleTypoGraphy.bodyMedium.merge(TextStyle(fontSize = 24.sp)),
            strokeColor = Color.White,
            strokeWidth = 1f
        )
        OutLinedText(
            modifier = Modifier.padding(vertical = 12.dp),
            onClickText = {
                viewModel.onTriggerEvent(
                    MyPageViewEvent.OnClickSignOut
                )
            },
            text = "회원탈퇴",
            textStyle = vodleTypoGraphy.bodySmall.merge(TextStyle(fontSize = 16.sp)),
            strokeColor = Color.White,
            strokeWidth = 1f
        )
    }
}

private fun fetchNaverAccessToken(): String =
    NaverIdLoginSDK.getAccessToken() ?: ""

@Composable
private fun TopBar(onClickText: () -> Unit) {
    Box {
        Image(
            alignment = Alignment.CenterStart,
            modifier = Modifier.clickable { onClickText() },
            painter = painterResource(id = R.drawable.left_arrow),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "마이페이지",
                style = vodleTypoGraphy.bodyLarge
            )
        }
    }
}
