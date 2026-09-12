package com.example.algorithmic_pasticceria

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform