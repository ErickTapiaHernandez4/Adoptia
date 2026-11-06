package com.example.adoptia.sensor

// En escritorio no hay sensor biométrico.
actual fun biometriaDisponible(): Boolean = false
actual fun autenticarConBiometria(alExito: () -> Unit, alError: (String) -> Unit) =
    alError("No disponible en escritorio")
actual fun guardarCredenciales(usuario: String, password: String) {}
actual fun leerCredenciales(): Pair<String, String>? = null