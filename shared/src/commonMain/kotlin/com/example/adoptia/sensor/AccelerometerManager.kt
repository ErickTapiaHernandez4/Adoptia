package com.example.adoptia.sensor

interface AccelerometerManager {
    fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit)
    fun stopListening()
}

expect fun createAccelerometerManager(): AccelerometerManager
