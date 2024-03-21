package com.tes.domain.repository

interface VodleRepository {

    suspend fun fetchVodlesAround()
}
