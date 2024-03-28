package com.tes.vodle.repository

import com.tes.domain.model.Location
import com.tes.domain.model.Vodle
import com.tes.domain.repository.VodleRepository
import com.tes.vodle.datasource.vodle.VodleDataSource
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
}
