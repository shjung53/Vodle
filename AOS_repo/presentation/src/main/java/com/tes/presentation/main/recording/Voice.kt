package com.tes.presentation.main.recording

enum class VoiceType {
    AHRI, MUNDO, OPTIMUSPRIME, TRUMP, ELSA, ORIGINAL
}

data class AudioData(
    val voiceType: VoiceType,
    val convertedAudioUrl: Url
)

typealias Url = String
