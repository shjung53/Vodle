package com.tes.vodle.api

import com.tes.vodle.model.user.response.TokenResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/")
    suspend fun refreshUserToken(
        @Header("refreshToken") refreshToken: String,
        @Header("Authorization") accessToken: String
    ): TokenResponse
}
