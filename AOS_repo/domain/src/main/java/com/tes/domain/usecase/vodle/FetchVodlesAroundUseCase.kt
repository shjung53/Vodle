package com.tes.domain.usecase.vodle

import com.tes.domain.repository.VodleRepository
import javax.inject.Inject

class FetchVodlesAroundUseCase @Inject constructor(
    private val vodleRepository: VodleRepository
) {
    suspend operator fun invoke() = vodleRepository.fetchVodlesAround()
}
