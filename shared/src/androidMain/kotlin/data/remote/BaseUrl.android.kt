package com.example.adoptia.data.remote

// Con "adb reverse tcp:8080 tcp:8080", el localhost del emulador
// apunta al localhost de la compu donde corre el backend.
actual fun obtenerBaseUrl(): String = "http://localhost:8080"