package com.example.adoptia.domain.sensor

import com.example.adoptia.domain.model.AccelerometerData

class JvmAccelerometerManager : AccelerometerManager {
    override fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit) {}
    override fun stopListening() {}
}

actual fun createAccelerometerManager(): AccelerometerManager = JvmAccelerometerManager()
