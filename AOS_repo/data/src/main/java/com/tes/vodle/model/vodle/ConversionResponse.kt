package com.tes.vodle.model.vodle

data class ConversionResponse(
    val status: Int,
    val message: String,
    val data: List<ConversionData>
)

data class ConversionData(
    val convertedFileUrl: String,
    val selectedVoice: String,
    val contentType: String
)
