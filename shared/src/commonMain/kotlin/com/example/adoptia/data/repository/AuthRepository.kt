package com.example.adoptia.data.repository

import com.example.adoptia.data.remote.ApiClient
import com.example.adoptia.data.remote.AuthApiService
import com.example.adoptia.domain.model.Rol
import com.example.adoptia.domain.model.SesionUsuario

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
        } catch (e: Exception) {
            ResultadoLogin.Error("Usuario o contraseña incorrectos, o el servidor no está disponible.")
        }
    }
}