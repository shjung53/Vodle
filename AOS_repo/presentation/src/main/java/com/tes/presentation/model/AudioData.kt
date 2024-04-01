package com.tes.presentation.model

enum class VoiceType(val korean: String, val eng: String) {
    AHRI("아리", "ahri"), MUNDO("문도", "mundo"),
    OPTIMUSPRIME("옵티머스프라임", "optimusPrime"),
    TRUMP("트럼프", "trump"), ELSA("엘사", "elsa"),
    ORIGINAL("내 목소리", "original")
}

data class AudioData(
    val voiceType: VoiceType = VoiceType.ORIGINAL,
    val convertedAudioUrl: String = ""
)

typealias Url = String
