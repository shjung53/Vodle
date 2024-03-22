package com.tes.vodle.datasource.vodle

import com.tes.vodle.model.vodle.VodlesAroundResponse

interface VodleDataSource {

    suspend fun fetchVodlesAround(): Result<VodlesAroundResponse>
}
