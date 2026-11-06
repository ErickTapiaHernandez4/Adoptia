package com.example.adoptia.sensor

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

// MainActivity se guarda aquí al abrir la app, porque BiometricPrompt la necesita.
object ActividadBiometria {
    var actividad: FragmentActivity? = null
}

// STRONG = huella; WEAK = rostro en la mayoría de los celulares.
private const val TIPOS = BIOMETRIC_STRONG or BIOMETRIC_WEAK

actual fun biometriaDisponible(): Boolean {
    val act = ActividadBiometria.actividad ?: return false
    return BiometricManager.from(act).canAuthenticate(TIPOS) == BiometricManager.BIOMETRIC_SUCCESS
}

actual fun autenticarConBiometria(alExito: () -> Unit, alError: (String) -> Unit) {
    val act = ActividadBiometria.actividad
    if (act == null) {
        alError("No se pudo abrir el sensor")
        return
    }

    val prompt = BiometricPrompt(
        act,
        ContextCompat.getMainExecutor(act),
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                alExito()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                alError(errString.toString())
            }
        }
    )

    val info = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Iniciar sesión en Adoptia")
        .setSubtitle("Usa tu huella o tu rostro")
        .setNegativeButtonText("Cancelar")
        .setAllowedAuthenticators(TIPOS)
        .build()

    prompt.authenticate(info)
}

actual fun guardarCredenciales(usuario: String, password: String) {
    val act = ActividadBiometria.actividad ?: return
    act.getSharedPreferences("adoptia", Context.MODE_PRIVATE)
        .edit()
        .putString("usuario", usuario)
        .putString("password", password)
        .apply()
}

actual fun leerCredenciales(): Pair<String, String>? {
    val act = ActividadBiometria.actividad ?: return null
    val prefs = act.getSharedPreferences("adoptia", Context.MODE_PRIVATE)
    val usuario = prefs.getString("usuario", null) ?: return null
    val password = prefs.getString("password", null) ?: return null
    return usuario to password
}