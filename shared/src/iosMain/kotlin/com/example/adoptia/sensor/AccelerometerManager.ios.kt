package com.example.adoptia.sensor

class IosAccelerometerManager : AccelerometerManager {
    override fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit) {}
    override fun stopListening() {}
}

actual fun createAccelerometerManager(): AccelerometerManager = IosAccelerometerManager()
