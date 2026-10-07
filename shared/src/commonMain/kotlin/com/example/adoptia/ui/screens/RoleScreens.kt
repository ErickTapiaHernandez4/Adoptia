package com.example.adoptia.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.adoptia.domain.model.SesionUsuario

// Plantillas por rol. Cada función ya recibe la sesión (nombre, rol) y el
// botón de cerrar sesión funcionando. Solo falta meter el contenido real.

@Composable
fun AdministradorScreen(sesion: SesionUsuario, onCerrarSesion: () -> Unit) {
    PlantillaRol(titulo = "Panel de Administrador", sesion = sesion, onCerrarSesion = onCerrarSesion) {
        Text("TODO: gestión de usuarios, refugios y reportes.")
    }
}

@Composable
fun ClienteScreen(sesion: SesionUsuario, onCerrarSesion: () -> Unit) {
    PlantillaRol(titulo = "Adoptar una mascota", sesion = sesion, onCerrarSesion = onCerrarSesion) {
        Text("TODO: ver mascotas disponibles, favoritos, solicitudes de adopción.")
    }
}

@Composable
fun RefugioScreen(sesion: SesionUsuario, onCerrarSesion: () -> Unit) {
    PlantillaRol(titulo = "Panel de Refugio", sesion = sesion, onCerrarSesion = onCerrarSesion) {
        Text("TODO: registrar mascotas, ver solicitudes recibidas.")
    }
}

@Composable
private fun PlantillaRol(
    titulo: String,
    sesion: SesionUsuario,
    onCerrarSesion: () -> Unit,
    contenido: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(titulo) }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text(text = "Hola, ${sesion.nombre}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Rol: ${sesion.rol}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.padding(top = 16.dp))
            contenido()
            Spacer(modifier = Modifier.padding(top = 24.dp))

            Button(onClick = onCerrarSesion) {
                Text("Cerrar sesión")
            }
        }
    }
}