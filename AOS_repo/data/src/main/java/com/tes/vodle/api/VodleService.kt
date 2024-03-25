package com.tes.vodle.api

import com.tes.vodle.model.vodle.VodlesAroundResponse
import retrofit2.http.GET

interface VodleService {
    @GET("api/vodle/all")
    suspend fun fetchVodlesAround(): VodlesAroundResponse


}
