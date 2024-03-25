package com.tes.vodle.repository

import com.tes.domain.TokenManager
import com.tes.domain.repository.UserRepository
import com.tes.vodle.datasource.user.UserDataSource
import com.tes.vodle.util.Encryption.calculateHmac
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val tokenManager: TokenManager
) : UserRepository {

    override suspend fun signInNaver(code: String): Result<Unit> {
        val signature = calculateHmac("$code&NAVER")
        val result = runBlocking { userDataSource.signInNaver(code, signature, "NAVER") }
        return result.fold(
            onSuccess = {
                tokenManager.saveToken(it.data.accessToken, it.data.refreshToken)
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun getNaverId(accessToken: String): Result<String> =
        userDataSource.getNaverLoginId(accessToken).fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = {
                Result.failure(it)
            }
        )

    override suspend fun signOutWithNaver(
        naverClientId: String,
        naverSecret: String,
        accessToken: String
    ): Result<Unit> =
        userDataSource.signOutWithNaver(naverClientId, naverSecret, accessToken).fold(
            onSuccess = {
                tokenManager.deleteTokens()
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(it)
            }
        )
}
