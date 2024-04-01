package com.tes.vodle.repository

import android.util.Log
import com.tes.domain.model.AudioData
import com.tes.domain.model.Gender
import com.tes.domain.model.Location
import com.tes.domain.model.Vodle
import com.tes.domain.repository.VodleRepository
import com.tes.vodle.datasource.vodle.VodleDataSource
import com.tes.vodle.util.toVoiceType
import java.io.File
import javax.inject.Inject

class VodleRepositoryImpl @Inject constructor(
    private val vodleDataSource: VodleDataSource
) : VodleRepository {
    override suspend fun fetchVodlesAround(): Result<List<Vodle>> =
        vodleDataSource.fetchVodlesAround().fold(
            onSuccess = { it ->
                val vodleList: List<Vodle> = it.dataBody.map {
                    Vodle(
                        it.id,
                        "테스트 날짜",
                        it.address,
                        it.writer,
                        it.category,
                        Location(it.latitude.toDouble(), it.longitude.toDouble()),
                        it.streamingURL
                    )
                }

                Result.success(vodleList)
            },
            onFailure = { Result.failure(Exception()) }
        )

    override suspend fun uploadVodle(recordingFile: File): Result<Unit> =
        vodleDataSource.uploadVodle(recordingFile).fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(Exception())
            }
        )

    override suspend fun convertVoice(
        recordingFile: File,
        selectedVoice: String,
        gender: Gender
    ): Result<AudioData> =
        vodleDataSource.convertVoice(recordingFile, selectedVoice, gender).fold(
            onSuccess = { it ->
                Result.success(
                    AudioData(
                        it.data.selectedVoice.toVoiceType(),
                        it.data.convertedFileUrl
                    )
                )
            },
            onFailure = { exception ->
                Log.d("확인", "데이터소스에서 삑")
                // 오류 처리
                Result.failure(exception)
            }
        )
}
