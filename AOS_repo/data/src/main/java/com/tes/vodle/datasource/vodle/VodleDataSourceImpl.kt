package com.tes.vodle.datasource.vodle

import com.tes.vodle.api.VodleService
import com.tes.vodle.model.vodle.VodlesAroundResponse
import javax.inject.Inject

class VodleDataSourceImpl @Inject constructor(
    private val vodleService: VodleService
) : VodleDataSource {
    override suspend fun fetchVodlesAround(): Result<VodlesAroundResponse> = runCatching {
        vodleService.fetchVodlesAround()
    }
}
