package com.tes.domain.repository

interface UserRepository {

    suspend fun signInNaver(code: String): Result<Unit>

    suspend fun getNaverId(accessToken: String): Result<String>

    suspend fun signOutWithNaver(
        naverClientId: String,
        naverSecret: String,
        accessToken: String
    ): Result<Unit>
}
