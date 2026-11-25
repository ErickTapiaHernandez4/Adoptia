package com.example.adoptia

class WasmPlatform : Platform {
    override val name: String = "Web con Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()