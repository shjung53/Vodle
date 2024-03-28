package com.tes.vodle.datasource.vodle

import com.tes.vodle.model.BasicResponse
import com.tes.vodle.model.vodle.ConversionResponse
import com.tes.vodle.model.vodle.VodlesAroundResponse
import java.io.File

interface VodleDataSource {

    suspend fun fetchVodlesAround(): Result<VodlesAroundResponse>

    suspend fun uploadVodle(recordingFile: File): Result<BasicResponse>

    suspend fun convertVoice(recordingFile: File): Result<ConversionResponse>
}
