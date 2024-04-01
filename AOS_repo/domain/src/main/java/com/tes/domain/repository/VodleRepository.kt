package com.tes.domain.repository

import com.tes.domain.model.AudioData
import com.tes.domain.model.Vodle
import java.io.File

interface VodleRepository {

    suspend fun fetchVodlesAround(): Result<List<Vodle>>

    suspend fun uploadVodle(recordingFile: File): Result<Unit>

    suspend fun convertVoice(recordingFile: File): Result<List<AudioData>>
}
