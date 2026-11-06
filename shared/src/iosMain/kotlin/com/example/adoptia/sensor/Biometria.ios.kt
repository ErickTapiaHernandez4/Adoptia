package com.example.adoptia.sensor

// En iOS no lo implementamos (la app se prueba en Android).
actual fun biometriaDisponible(): Boolean = false
actual fun autenticarConBiometria(alExito: () -> Unit, alError: (String) -> Unit) =
    alError("No disponible en iOS")
actual fun guardarCredenciales(usuario: String, password: String) {}
actual fun leerCredenciales(): Pair<String, String>? = null