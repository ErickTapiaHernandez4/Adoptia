package com.example.adoptia.data.repository

import com.example.adoptia.data.remote.ApiClient
import com.example.adoptia.data.remote.AuthApiService
import com.example.adoptia.domain.model.Rol
import com.example.adoptia.domain.model.SesionUsuario
import io.ktor.client.plugins.ClientRequestException

sealed class ResultadoLogin {
    data class Exito(val sesion: SesionUsuario) : ResultadoLogin()
    data class Error(val mensaje: String) : ResultadoLogin()
}

class AuthRepository(
    private val servicio: AuthApiService = AuthApiService(ApiClient.client)
) {
    suspend fun login(usuario: String, password: String): ResultadoLogin {
        return try {
            val respuesta = servicio.login(usuario, password)
            ResultadoLogin.Exito(
                SesionUsuario(
                    token = respuesta.token,
                    rol = Rol.desdeTexto(respuesta.rol),
                    nombre = respuesta.nombre
                )
            )
        } catch (e: ClientRequestException) {
            // El servidor respondió 401: usuario o contraseña no están en usuarios.json
            ResultadoLogin.Error("Usuario o contraseña incorrectos")
        } catch (e: Exception) {
            // No hubo respuesta: el backend está apagado o no se alcanza
            ResultadoLogin.Error("No se pudo conectar con el servidor")
        }
    }
}