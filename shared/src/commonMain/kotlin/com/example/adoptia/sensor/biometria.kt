package com.example.adoptia.sensor

// Sensor biométrico (huella o rostro). Cada plataforma lo implementa con "actual".

/** true si el celular tiene huella o rostro registrado y se puede usar. */
expect fun biometriaDisponible(): Boolean

/** Abre la ventana del sistema para poner la huella o el rostro. */
expect fun autenticarConBiometria(alExito: () -> Unit, alError: (String) -> Unit)

/** Guarda el último usuario que entró con contraseña. */
expect fun guardarCredenciales(usuario: String, password: String)

/** Regresa el usuario y la contraseña guardados, o null si no hay. */
expect fun leerCredenciales(): Pair<String, String>?