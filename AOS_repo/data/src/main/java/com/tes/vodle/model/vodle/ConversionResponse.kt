package com.tes.vodle.model.vodle

data class ConversionResponse(
    val status: Int,
    val message: String,
    val data: ConversionData
)

data class ConversionData(
    val convertedFile: String,
    val fileName: String,
    val selectedVoice: String,
    val contentType: String
)
