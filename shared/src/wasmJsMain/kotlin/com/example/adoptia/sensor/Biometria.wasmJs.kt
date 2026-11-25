package com.example.adoptia.sensor

// En el navegador no hay huella ni rostro: el botón no aparece.
actual fun biometriaDisponible(): Boolean = false
actual fun autenticarConBiometria(alExito: () -> Unit, alError: (String) -> Unit) =
    alError("No disponible en la web")
actual fun guardarCredenciales(usuario: String, password: String) {}
actual fun leerCredenciales(): Pair<String, String>? = null