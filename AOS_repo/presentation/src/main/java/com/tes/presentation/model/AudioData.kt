package com.tes.presentation.model

enum class VoiceType(val korean: String) {
    AHRI("아리"), MUNDO("문도"), OPTIMUSPRIME("옵티머스프라임"),
    TRUMP("트럼프"), ELSA("엘사"), ORIGINAL("내 목소리")
}

data class AudioData(
    val voiceType: VoiceType,
    val convertedAudioUrl: Url
)

typealias Url = String
