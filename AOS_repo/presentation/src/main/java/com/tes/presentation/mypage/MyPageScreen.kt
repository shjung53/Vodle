package com.tes.presentation.mypage

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.tes.presentation.R
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
    val context = LocalContext.current

    ObserveToastMessage(viewState = viewState, context = context, viewModel = viewModel)

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

    when (viewState) {
        is MyPageViewState.Default -> MyPageView(onClickBackButton, viewModel)
        is MyPageViewState.VodleLog -> VodleLogView(vodleLogList = viewState.vodleLogList) {
            viewModel.onTriggerEvent(MyPageViewEvent.OnClickBackButtonFromVodleLogView)
        }
    }
}

private fun fetchNaverAccessToken(): String =
    NaverIdLoginSDK.getAccessToken() ?: ""

@Composable
internal fun TopBar(onClickBackButton: () -> Unit, title: String) {
    Box {
        Image(
            alignment = Alignment.CenterStart,
            modifier = Modifier.clickable { onClickBackButton() },
            painter = painterResource(id = R.drawable.left_arrow),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = vodleTypoGraphy.bodyLarge
            )
        }
    }
}

@Composable
private fun ObserveToastMessage(
    viewState: MyPageViewState,
    context: Context,
    viewModel: MyPageViewModel
) {
    LaunchedEffect(key1 = viewState.toastMessage) {
        if (viewState.toastMessage.isNotEmpty()) {
            Toast.makeText(context, viewState.toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.onTriggerEvent(MyPageViewEvent.OnFinishToast)
        }
    }
}
