package com.tes.presentation.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.tes.presentation.R
import com.tes.presentation.navigation.Route
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val viewState = viewModel.uiState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(key1 = viewState.isTryingLogin) {
        if (viewState.isTryingLogin) {
            authenticateWithNaver(context = context)
        }
    }

    ObserveLoginSuccess(viewState, onLoginSuccess)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFC8DCDC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_panpare),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Text(text = "Vodle", fontSize = 72.sp)

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .width(280.dp)
                .height(40.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { viewModel.onTriggerEvent(LoginViewEvent.OnClickNaverLoginButton) },
            painter = painterResource(id = R.drawable.naver_login_button),
            contentDescription = null
        )

        Spacer(modifier = Modifier.weight(1.1f))
    }
}

@Composable
private fun ObserveLoginSuccess(
    viewState: LoginViewState,
    onLoginSuccess: () -> Unit
) {
    LaunchedEffect(key1 = viewState) {
        if (viewState.nextRoute != Route.LOGIN) {
            onLoginSuccess()
        }
    }
}

private suspend fun authenticateWithNaver(context: Context): Result<String> =
    suspendCancellableCoroutine { continuation ->
        val callback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val accessToken = NaverIdLoginSDK.getAccessToken() ?: ""
                continuation.resume(Result.success(accessToken))
            }

            override fun onFailure(httpStatus: Int, message: String) {
                continuation.resumeWithException(
                    Exception("Authentication failed: $message (HTTP $httpStatus)")
                )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(context, callback)

        continuation.invokeOnCancellation {
        }
    }
