package com.example.adoptia.domain.model

enum class Rol {
    ADMINISTRADOR,
    CLIENTE,
    REFUGIO;

    companion object {
        fun desdeTexto(texto: String): Rol =
            entries.find { it.name.equals(texto, ignoreCase = true) } ?: CLIENTE
    }
}