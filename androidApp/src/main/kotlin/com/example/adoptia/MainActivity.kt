package com.example.adoptia

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.example.adoptia.sensor.ActividadBiometria
import com.example.adoptia.domain.sensor.appContext as contextoDomainSensor
import com.example.adoptia.sensor.appContext as contextoSensor

// FragmentActivity (en vez de ComponentActivity) porque BiometricPrompt la necesita.
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        //Prueba examen).

        // Contexto para el sensor acelerómetro (hay dos versiones en el proyecto).
        contextoDomainSensor = applicationContext
        contextoSensor = applicationContext

        // Actividad para el sensor biométrico (huella o rostro).
        ActividadBiometria.actividad = this

        setContent {
            App()
        }
    }

    override fun onDestroy() {
        if (ActividadBiometria.actividad === this) ActividadBiometria.actividad = null
        super.onDestroy()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}