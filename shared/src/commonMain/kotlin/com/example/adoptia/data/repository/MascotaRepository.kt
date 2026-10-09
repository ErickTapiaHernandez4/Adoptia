package com.example.adoptia.data.repository

import com.example.adoptia.data.remote.ApiClient
import com.example.adoptia.data.remote.MascotaApiService
import com.example.adoptia.data.remote.MascotaDto
import com.example.adoptia.data.remote.SolicitudDto

class MascotaRepository(
    private val servicio: MascotaApiService = MascotaApiService(ApiClient.client)
) {
    suspend fun mascotas(): List<MascotaDto> = servicio.mascotas()

    suspend fun agregarMascota(mascota: MascotaDto) = servicio.agregarMascota(mascota)

    suspend fun solicitudes(): List<SolicitudDto> = servicio.solicitudes()

    suspend fun crearSolicitud(mascotaId: Int, cliente: String) =
        servicio.crearSolicitud(SolicitudDto(mascotaId = mascotaId, cliente = cliente))

    suspend fun aprobar(id: Int) = servicio.cambiarSolicitud(id, "aprobar")

    suspend fun rechazar(id: Int) = servicio.cambiarSolicitud(id, "rechazar")
}