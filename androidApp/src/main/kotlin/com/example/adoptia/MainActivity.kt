package com.example.adoptia

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.example.adoptia.sensor.ActividadBiometria

// FragmentActivity (en vez de ComponentActivity) porque BiometricPrompt la necesita.
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

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