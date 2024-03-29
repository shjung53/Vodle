package com.tes.vodle.model.vodle

import com.google.gson.annotations.SerializedName

data class VodlesAroundResponse(
    @SerializedName("dataHeader") val dataHeader: DataHeader,
    @SerializedName("data") val dataBody: List<VodleResponse>
)

data class VodleResponse(
    @SerializedName("voiceId") val id: Long = -1,
    @SerializedName("writer") val writer: String = "",
    @SerializedName("contentType") val category: String = "",
    @SerializedName("address") val address: String = "",
    @SerializedName("latitude") val latitude: Float = 0f,
    @SerializedName("longitude") val longitude: Float = 0f,
    @SerializedName("streamingURL") val streamingURL: String = "",
    @SerializedName("createdDate") val date: String = ""
)

data class DataHeader(
    val successCode: Int,
    val resultCode: String,
    val resultMessage: String
)
