package com.example.adoptia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptia.data.remote.MascotaDto
import com.example.adoptia.data.remote.SolicitudDto
import com.example.adoptia.data.repository.MascotaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MascotasUiState(
    val mascotas: List<MascotaDto> = emptyList(),
    val solicitudes: List<SolicitudDto> = emptyList(),
    val mensaje: String? = null,
    val cargando: Boolean = false
)

class MascotaViewModel(
    private val repository: MascotaRepository = MascotaRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(MascotasUiState())
    val estado: StateFlow<MascotasUiState> = _estado

    fun cargar() = ejecutar(null) { }

    fun adoptar(mascotaId: Int, cliente: String) =
        ejecutar("Solicitud enviada") { repository.crearSolicitud(mascotaId, cliente) }

    fun agregarMascota(mascota: MascotaDto) =
        ejecutar("Mascota agregada") { repository.agregarMascota(mascota) }

    fun aprobar(id: Int) = ejecutar("Solicitud aprobada") { repository.aprobar(id) }

    fun rechazar(id: Int) = ejecutar("Solicitud rechazada") { repository.rechazar(id) }

    // Hace la acción, y después vuelve a pedir las listas a la base de datos.
    private fun ejecutar(mensajeExito: String?, accion: suspend () -> Unit) {
        viewModelScope.launch {
            _estado.value = _estado.value.copy(cargando = true, mensaje = null)
            try {
                accion()
                _estado.value = MascotasUiState(
                    mascotas = repository.mascotas(),
                    solicitudes = repository.solicitudes(),
                    mensaje = mensajeExito
                )
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    cargando = false,
                    mensaje = "No se pudo conectar con el servidor"
                )
            }
        }
    }
}