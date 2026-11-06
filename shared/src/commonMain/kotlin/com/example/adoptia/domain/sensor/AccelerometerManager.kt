package com.example.adoptia.domain.sensor

import com.example.adoptia.domain.model.AccelerometerData

interface AccelerometerManager {
    fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit)
    fun stopListening()
}

expect fun createAccelerometerManager(): AccelerometerManager
