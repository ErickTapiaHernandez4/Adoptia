package com.example.adoptia.domain.model

data class SesionUsuario(
    val token: String,
    val rol: Rol,
    val nombre: String
)