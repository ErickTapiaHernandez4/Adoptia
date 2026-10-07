package com.example.adoptia.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val usuario: String,
    val password: String
)

@Serializable
data class LoginResponseDto(
    val token: String,
    val rol: String,
    val nombre: String
)