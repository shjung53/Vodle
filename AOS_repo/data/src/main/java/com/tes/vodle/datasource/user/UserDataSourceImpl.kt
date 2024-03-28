package com.tes.vodle.datasource.user

import android.util.Log
import com.tes.vodle.api.NaverAuthService
import com.tes.vodle.api.NaverLoginService
import com.tes.vodle.api.UserService
import com.tes.vodle.model.user.request.NaverLoginRequest
import com.tes.vodle.model.user.response.TokenResponse
import javax.inject.Inject

private const val TAG = "UserDataSourceImpl_싸피"
class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
    private val naverLoginService: NaverLoginService,
    private val naverAuthService: NaverAuthService
) : UserDataSource {
    override suspend fun getNaverLoginId(accessToken: String): Result<String> = runCatching {
        Log.d(TAG, "getNaverLoginId: ${accessToken}")
        naverLoginService.getNaverUserId("Bearer $accessToken").response.id
    }

    override suspend fun signInNaver(
        userCode: String,
        signature: String,
        provider: String
    ): Result<TokenResponse> = runCatching {
        userService.signInNaver(NaverLoginRequest(userCode, provider, signature))
    }

    override suspend fun signOutWithNaver(
        naverClientId: String,
        naverSecret: String,
        accessToken: String
    ): Result<Unit> = runCatching {
        naverAuthService.signOutWithNaver(
            naverClientId,
            naverSecret,
            accessToken
        )
    }
}
