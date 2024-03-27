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
                Result.success(
                    it.dataBody.map {
                        Vodle(
                            it.id,
                            "날짜",
                            it.address,
                            "작성자",
                            it.category,
                            Location(it.latitude.toDouble(), it.longitude.toDouble())
                        )
                    }
                )
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
