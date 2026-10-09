package com.example.adoptia.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class MascotaDto(
    val id: Int = 0,
    val nombre: String,
    val tipo: String,
    val edad: String,
    val refugio: String
)

@Serializable
data class SolicitudDto(
    val id: Int = 0,
    val mascotaId: Int,
    val mascota: String = "",
    val cliente: String,
    val estado: String = "PENDIENTE"
)