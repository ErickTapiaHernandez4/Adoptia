package com.example.adoptia.domain.sensor

import com.example.adoptia.domain.model.AccelerometerData

class IosAccelerometerManager : AccelerometerManager {
    override fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit) {}
    override fun stopListening() {}
}

actual fun createAccelerometerManager(): AccelerometerManager = IosAccelerometerManager()
