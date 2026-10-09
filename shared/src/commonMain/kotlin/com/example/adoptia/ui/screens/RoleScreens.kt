package com.example.adoptia.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.adoptia.data.remote.MascotaDto
import com.example.adoptia.domain.model.SesionUsuario
import com.example.adoptia.ui.viewmodel.MascotaViewModel
import com.example.adoptia.ui.viewmodel.MascotasUiState

// Tres vistas sencillas, una por rol. Los datos salen de MySQL a través del
// backend (MascotaViewModel -> MascotaRepository -> MascotaApiService).

@Composable
fun AdministradorScreen(sesion: SesionUsuario, onCerrarSesion: () -> Unit) {
    val vm = remember { MascotaViewModel() }

    PlantillaRol("Panel de Administrador", sesion, vm, onCerrarSesion) { estado ->
        Text("Mascotas registradas: ${estado.mascotas.size}")
        Text("Solicitudes: ${estado.solicitudes.size}")
        Spacer(modifier = Modifier.height(12.dp))

        Text("Solicitudes de adopción", style = MaterialTheme.typography.titleMedium)
        if (estado.solicitudes.isEmpty()) {
            Text("No hay solicitudes")
        }
        estado.solicitudes.forEach { s ->
            Text("#${s.id} ${s.cliente} quiere adoptar a ${s.mascota} - ${s.estado}")
            if (s.estado == "PENDIENTE") {
                Row {
                    Button(onClick = { vm.aprobar(s.id) }) { Text("Aprobar") }
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Button(onClick = { vm.rechazar(s.id) }) { Text("Rechazar") }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ClienteScreen(sesion: SesionUsuario, onCerrarSesion: () -> Unit) {
    val vm = remember { MascotaViewModel() }

    PlantillaRol("Adoptar una mascota", sesion, vm, onCerrarSesion) { estado ->
        Text("Mascotas disponibles", style = MaterialTheme.typography.titleMedium)
        if (estado.mascotas.isEmpty()) {
            Text("No hay mascotas")
        }
        estado.mascotas.forEach { m ->
            val yaPedida = estado.solicitudes.any {
                it.mascotaId == m.id && it.cliente == sesion.nombre
            }
            Text("${m.nombre} - ${m.tipo}, ${m.edad} (${m.refugio})")
            Button(
                onClick = { vm.adoptar(m.id, sesion.nombre) },
                enabled = !yaPedida
            ) {
                Text(if (yaPedida) "Solicitud enviada" else "Adoptar")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Mis solicitudes", style = MaterialTheme.typography.titleMedium)
        val mias = estado.solicitudes.filter { it.cliente == sesion.nombre }
        if (mias.isEmpty()) {
            Text("Todavía no has pedido ninguna")
        }
        mias.forEach { s ->
            Text("${s.mascota}: ${s.estado}")
        }
    }
}

@Composable
fun RefugioScreen(sesion: SesionUsuario, onCerrarSesion: () -> Unit) {
    val vm = remember { MascotaViewModel() }
    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    PlantillaRol("Panel de Refugio", sesion, vm, onCerrarSesion) { estado ->
        Text("Registrar mascota", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo (Perro, Gato...)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (nombre.isNotBlank() && tipo.isNotBlank() && edad.isNotBlank()) {
                    vm.agregarMascota(
                        MascotaDto(
                            nombre = nombre.trim(),
                            tipo = tipo.trim(),
                            edad = edad.trim(),
                            refugio = sesion.nombre
                        )
                    )
                    nombre = ""
                    tipo = ""
                    edad = ""
                }
            }
        ) {
            Text("Agregar mascota")
        }

        Spacer(modifier = Modifier.height(12.dp))
        val misMascotas = estado.mascotas.filter { it.refugio == sesion.nombre }
        Text("Mis mascotas (${misMascotas.size})", style = MaterialTheme.typography.titleMedium)
        misMascotas.forEach { m ->
            Text("${m.nombre} - ${m.tipo}, ${m.edad}")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Solicitudes recibidas", style = MaterialTheme.typography.titleMedium)
        val ids = misMascotas.map { it.id }
        val recibidas = estado.solicitudes.filter { it.mascotaId in ids }
        if (recibidas.isEmpty()) {
            Text("No hay solicitudes")
        }
        recibidas.forEach { s ->
            Text("${s.cliente} quiere a ${s.mascota} - ${s.estado}")
        }
    }
}

@Composable
private fun PlantillaRol(
    titulo: String,
    sesion: SesionUsuario,
    vm: MascotaViewModel,
    onCerrarSesion: () -> Unit,
    contenido: @Composable (MascotasUiState) -> Unit
) {
    val estado by vm.estado.collectAsState()

    // Al abrir la pantalla se piden los datos a la base de datos.
    LaunchedEffect(Unit) { vm.cargar() }

    Scaffold(
        topBar = { TopAppBar(title = { Text(titulo) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Hola, ${sesion.nombre}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Rol: ${sesion.rol}", style = MaterialTheme.typography.bodySmall)

            val mensaje = estado.mensaje
            if (mensaje != null) {
                Text(text = mensaje, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))
            contenido(estado)
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onCerrarSesion) {
                Text("Cerrar sesión")
            }
        }
    }
}