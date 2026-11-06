package com.example.adoptia.domain.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager as AndroidSensorManager
import com.example.adoptia.domain.model.AccelerometerData
import kotlin.math.sqrt

class AndroidAccelerometerManager(private val context: Context) : AccelerometerManager, SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as AndroidSensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var listener: ((AccelerometerData) -> Unit)? = null
    private var shakeListener: (() -> Unit)? = null
    private var lastShakeTime: Long = 0

    override fun startListening(onSensorChanged: (AccelerometerData) -> Unit, onShake: () -> Unit) {
        listener = onSensorChanged
        shakeListener = onShake
        accelerometer?.let {
            sensorManager.registerListener(this, it, AndroidSensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun stopListening() {
        try {
            sensorManager.unregisterListener(this)
        } catch (_: Exception) {}
        listener = null
        shakeListener = null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            listener?.invoke(AccelerometerData(x, y, z))

            // Check shake (G-force calculation)
            val gForce = sqrt((x * x + y * y + z * z).toDouble()) / 9.807
            if (gForce > 2.2) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastShakeTime > 1500) {
                    lastShakeTime = currentTime
                    shakeListener?.invoke()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

lateinit var appContext: Context

actual fun createAccelerometerManager(): AccelerometerManager {
    return AndroidAccelerometerManager(appContext)
}
