package com.example.adoptia.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MascotaApiService(private val client: HttpClient) {

    suspend fun mascotas(): List<MascotaDto> =
        client.get("${ApiClient.BASE_URL}/api/mascotas").body()

    suspend fun agregarMascota(mascota: MascotaDto) {
        client.post("${ApiClient.BASE_URL}/api/mascotas") {
            contentType(ContentType.Application.Json)
            setBody(mascota)
        }
    }

    suspend fun solicitudes(): List<SolicitudDto> =
        client.get("${ApiClient.BASE_URL}/api/solicitudes").body()

    suspend fun crearSolicitud(solicitud: SolicitudDto) {
        client.post("${ApiClient.BASE_URL}/api/solicitudes") {
            contentType(ContentType.Application.Json)
            setBody(solicitud)
        }
    }

    // accion: "aprobar" o "rechazar"
    suspend fun cambiarSolicitud(id: Int, accion: String) {
        client.post("${ApiClient.BASE_URL}/api/solicitudes/$id/$accion")
    }
}