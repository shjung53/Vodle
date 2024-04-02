package com.tes.vodle.api

import com.tes.vodle.model.BasicResponse
import com.tes.vodle.model.vodle.ConversionResponse
import com.tes.vodle.model.vodle.TTSConversionRequest
import com.tes.vodle.model.vodle.VodlesAroundResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface VodleService {
    @POST("api/vodle/search")
    suspend fun fetchVodlesAround(
        @Body vodlesAroundRequest: VodlesAroundRequest
    ): VodlesAroundResponse

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
    @POST("api/vodle/conversion/{selected_voice}/{gender}")
    suspend fun convertVoice(
        @Part soundFile: MultipartBody.Part,
        @Path(value = "selected_voice") selectedVoice: String = "mundo",
        @Path(value = "gender") gender: String = "male"
    ): ConversionResponse

    @POST("api/vodle/tts")
    suspend fun convertTTS(
        @Body ttsConversionRequest: TTSConversionRequest
    ): ConversionResponse
}

data class VodleMetaData(
    val writer: String = " 익명",
    val recordType: String = "TTS",
    val fileOriginName: String = "test",
    val longitude: Float = 128.41647f,
    val latitude: Float = 36.10714f
)
