package com.tes.presentation.model

sealed class Vodle {
    abstract val id: Long
    abstract val date: String
    abstract val address: String

    data class VodleForLog(
        override val id: Long,
        override val date: String,
        override val address: String
    ) : Vodle()

    data class VodleForMap(
        override val id: Long,
        override val date: String,
        override val address: String,
        val writer: String,
        val category: String,
        val location: Location
    ) : Vodle()
}
