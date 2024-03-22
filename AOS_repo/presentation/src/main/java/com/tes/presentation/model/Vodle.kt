package com.tes.presentation.model

data class VodleForLog(
    val id: Long,
    val date: String,
    val address: String
)

data class VodleForMap(
    val id: Long,
    val date: String,
    val address: String,
    val writer: String,
    val category: String,
    val location: Location
)
