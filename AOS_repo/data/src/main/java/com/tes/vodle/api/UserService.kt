package com.tes.vodle.api

import com.tes.vodle.model.user.request.NaverLoginRequest
import com.tes.vodle.model.user.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/users/sign-in-naver")
    suspend fun signInNaver(
        @Body naverLoginRequest: NaverLoginRequest
    ): Result<TokenResponse>
}
