package com.example.adoptia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.adoptia.domain.model.Rol
import com.example.adoptia.domain.model.SesionUsuario
import com.example.adoptia.sensor.AccelerometerScreen
import com.example.adoptia.ui.screens.AdministradorScreen
import com.example.adoptia.ui.screens.ClienteScreen
import com.example.adoptia.ui.screens.LoginScreen
import com.example.adoptia.ui.screens.RefugioScreen
import com.example.adoptia.ui.viewmodel.LoginViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var sesion by remember { mutableStateOf<SesionUsuario?>(null) }
        var showSensor by remember { mutableStateOf(false) }
        val loginViewModel = remember { LoginViewModel() }
        val sesionActual = sesion

        if (showSensor) {
            AccelerometerScreen(onBack = { showSensor = false })
        } else if (sesionActual == null) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginExitoso = { nuevaSesion -> sesion = nuevaSesion }
            )
        } else {
            val cerrarSesion: () -> Unit = {
                loginViewModel.cerrarSesion()
                sesion = null
            }
            when (sesionActual.rol) {
                Rol.ADMINISTRADOR -> AdministradorScreen(sesionActual, cerrarSesion, onOpenSensor = { showSensor = true })
                Rol.CLIENTE -> ClienteScreen(sesionActual, cerrarSesion, onOpenSensor = { showSensor = true })
                Rol.REFUGIO -> RefugioScreen(sesionActual, cerrarSesion, onOpenSensor = { showSensor = true })
            }
        }
    }
}
