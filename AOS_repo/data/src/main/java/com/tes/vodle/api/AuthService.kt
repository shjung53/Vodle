package com.tes.vodle.api

import com.tes.vodle.model.user.response.TokenResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthService {
    @GET("api/auth/social")
    fun refreshUserToken(@Header("refreshToken") token: String): TokenResponse
}
