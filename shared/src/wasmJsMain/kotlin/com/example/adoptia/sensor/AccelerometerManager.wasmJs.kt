package com.example.adoptia.sensor

// En la computadora no hay acelerómetro: no hace nada.
class WasmAccelerometerManager : AccelerometerManager {
    override fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit) {}
    override fun stopListening() {}
}

actual fun createAccelerometerManager(): AccelerometerManager = WasmAccelerometerManager()