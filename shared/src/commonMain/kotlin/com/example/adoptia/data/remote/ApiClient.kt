package com.example.adoptia.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    // 10.0.2.2 es como el emulador de Android llega al "localhost" de tu
    // compu, donde corre AdoptiaBackend. Si prueban en celular físico,
    // cambien esto por la IP real de la compu en la red.
    const val BASE_URL = "http://10.0.2.2:8080"

    val client: HttpClient = HttpClient {
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