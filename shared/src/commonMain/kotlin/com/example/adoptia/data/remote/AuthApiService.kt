package com.example.adoptia.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiService(private val client: HttpClient) {

    suspend fun login(usuario: String, password: String): LoginResponseDto {
        val respuesta = client.post("${ApiClient.BASE_URL}/api/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(usuario, password))
        }
        return respuesta.body()
    }
}