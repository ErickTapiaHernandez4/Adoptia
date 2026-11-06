package com.example.adoptia.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.adoptia.data.remote.MascotaDto
import com.example.adoptia.data.repository.MascotaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccelerometerViewModel(
    private val mascotaRepository: MascotaRepository = MascotaRepository()
) {
    private val accelerometerManager = createAccelerometerManager()

    var accelerometerData by mutableStateOf(AccelerometerData())
        private set

    var isListening by mutableStateOf(false)
        private set

    var mascotaSorpresa by mutableStateOf<MascotaDto?>(null)
        private set

    var showDialog by mutableStateOf(false)
        private set

    fun startListening() {
        if (!isListening) {
            isListening = true
            accelerometerManager.startListening(
                onSensorChanged = { data ->
                    accelerometerData = data
                },
                onShake = {
                    triggerShakeAction()
                }
            )
        }
    }

    fun stopListening() {
        if (isListening) {
            isListening = false
            accelerometerManager.stopListening()
        }
    }

    fun triggerShakeAction() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val mascotas = mascotaRepository.mascotas()
                if (mascotas.isNotEmpty()) {
                    mascotaSorpresa = mascotas.random()
                    showDialog = true
                }
            } catch (_: Exception) {
                mascotaSorpresa = MascotaDto(id = 0, nombre = "Firu (Demo)", tipo = "Perro", edad = "2 años", refugio = "Refugio Central")
                showDialog = true
            }
        }
    }

    fun dismissDialog() {
        showDialog = false
    }
}
