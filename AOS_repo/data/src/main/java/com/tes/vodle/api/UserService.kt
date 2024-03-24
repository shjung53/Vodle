package com.tes.vodle.api

import com.tes.vodle.model.user.request.NaverLoginRequest
import com.tes.vodle.model.user.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/auth/social")
    suspend fun signInNaver(
        @Body naverLoginRequest: NaverLoginRequest
    ): TokenResponse
}
