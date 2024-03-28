package com.tes.vodle.api

import com.tes.vodle.model.BasicResponse
import com.tes.vodle.model.vodle.VodlesAroundResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface VodleService {
    @GET("api/vodle/all")
    suspend fun fetchVodlesAround(): VodlesAroundResponse

    @Multipart
    @POST("api/vodle")
    suspend fun uploadVodle(
        @Part soundFile: MultipartBody.Part,
        @Part("vodleCreateReqDto") vodleMetaData: VodleMetaData
    ): BasicResponse

    /***
     * @param selectedVoice
     * "ahri", "mundo", "optimusPrime", "trump", "elsa" 중 하나의 목소리 선택
     */
    @Multipart
    @POST("api/vodle/conversion/{selected_voice}/{pitch_change}")
    suspend fun convertVoice(
        @Part soundFile: MultipartBody.Part,
        @Path(value = "selected_voice") selectedVoice: String = "mundo",
        @Path(value = "pitch_change") pitch: Int = 0
    ): String
}

data class VodleMetaData(
    val writer: String = " tester",
    val recordType: String = "TTS",
    val fileOriginName: String = "test",
    val longitude: Float = 128.41647f,
    val latitude: Float = 36.10714f
)
