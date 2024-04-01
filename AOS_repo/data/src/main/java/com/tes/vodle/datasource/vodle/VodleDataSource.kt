package com.tes.vodle.datasource.vodle

import com.tes.domain.model.Gender
import com.tes.domain.model.Location
import com.tes.vodle.model.BasicResponse
import com.tes.vodle.model.vodle.ConversionResponse
import com.tes.vodle.model.vodle.VodlesAroundResponse
import java.io.File

interface VodleDataSource {

    suspend fun fetchVodlesAround(
        centerLocation: Location,
        northEastLocation: Location,
        southWestLocation: Location
    ): Result<VodlesAroundResponse>

    suspend fun uploadVodle(recordingFile: File): Result<BasicResponse>

    suspend fun convertVoice(
        recordingFile: File,
        selectedVoice: String,
        gender: Gender
    ): Result<ConversionResponse>
}
