package com.tes.vodle.model.user.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
