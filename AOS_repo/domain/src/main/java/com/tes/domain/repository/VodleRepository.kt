package com.tes.domain.repository

import com.tes.domain.model.Location
import com.tes.domain.model.Vodle

interface VodleRepository {

    suspend fun fetchVodlesAround(): Result<List<Vodle>>
}
