package com.tes.domain.usecase.vodle

import com.tes.domain.repository.VodleRepository
import java.io.File
import javax.inject.Inject

class ConvertRecordingUseCase @Inject constructor(
    private val vodleRepository: VodleRepository
) {
    suspend operator fun invoke(recordingFile: File) = vodleRepository.convertVoice(recordingFile)
}
