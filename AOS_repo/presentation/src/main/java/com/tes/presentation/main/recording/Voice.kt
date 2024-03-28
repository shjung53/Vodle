package com.tes.presentation.main.recording

import com.tes.presentation.utils.AudioDataString

enum class VoiceType {
    AHRI, MUNDO, OPTIMUSPRIME, TRUMP, ELSA, ORIGINAL
}

data class AudioData(
    val voiceType: VoiceType,
    val audioDataString: AudioDataString
)
