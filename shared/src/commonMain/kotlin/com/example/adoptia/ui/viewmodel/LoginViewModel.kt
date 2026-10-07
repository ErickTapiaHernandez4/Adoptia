package com.example.adoptia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptia.data.repository.AuthRepository
import com.example.adoptia.data.repository.ResultadoLogin
import com.example.adoptia.domain.model.SesionUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val cargando: Boolean = false,
    val error: String? = null,
    val sesion: SesionUsuario? = null
)

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado

    fun login(usuario: String, password: String) {
        if (usuario.isBlank() || password.isBlank()) {
            _estado.value = _estado.value.copy(error = "Escribe usuario y contraseña")
            return
        }

        _estado.value = _estado.value.copy(cargando = true, error = null)

        viewModelScope.launch {
            val resultado = repository.login(usuario, password)
            _estado.value = when (resultado) {
                is ResultadoLogin.Exito -> LoginUiState(sesion = resultado.sesion)
                is ResultadoLogin.Error -> LoginUiState(error = resultado.mensaje)
            }
        }
    }

    fun cerrarSesion() {
        _estado.value = LoginUiState()
    }
}