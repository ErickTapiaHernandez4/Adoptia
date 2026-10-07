package com.example.adoptia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform