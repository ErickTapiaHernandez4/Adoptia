package com.example.adoptia.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    // La dirección del backend cambia según la plataforma (ver BaseUrl.*.kt):
    // Android usa 10.0.2.2 (así el emulador llega al localhost de tu compu).
    // Si prueban en celular físico, cambien la de Android por la IP real
    // de la compu en la red.
    val BASE_URL: String = obtenerBaseUrl()

    val client: HttpClient = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }
}