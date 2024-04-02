package com.tes.domain.repository

import com.tes.domain.model.AudioData
import com.tes.domain.model.Gender
import com.tes.domain.model.Location
import com.tes.domain.model.Vodle
import java.io.File

interface VodleRepository {

    suspend fun fetchVodlesAround(
        centerLocation: Location,
        northEastLocation: Location,
        southWestLocation: Location
    ): Result<List<Vodle>>

    suspend fun uploadVodle(recordingFile: File): Result<Unit>

    suspend fun convertVoice(
        recordingFile: File,
        selectedVoice: String,
        gender: Gender
    ): Result<AudioData>
}
