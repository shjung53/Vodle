package com.tes.vodle.api

import com.tes.vodle.model.BasicResponse
import com.tes.vodle.model.vodle.VodlesAroundResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface VodleService {
    @GET("api/vodle/all")
    suspend fun fetchVodlesAround(): VodlesAroundResponse

    @Multipart
    @POST("api/vodle")
    suspend fun uploadVodle(
        @Part soundFile: MultipartBody.Part,
        @Part("vodleCreateReqDto") vodleMetaData: VodleMetaData
    ): BasicResponse
}

data class VodleMetaData(
    val writer: String = " tester",
    val recordType: String = "TTS",
    val fileOriginName: String = "test",
    val longitude: Float = 36.10714f,
    val latitude: Float = 128.41647f
)
