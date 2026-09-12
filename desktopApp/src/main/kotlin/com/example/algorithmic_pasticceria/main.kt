package com.example.algorithmic_pasticceria

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Algorithmic Pasticceria 🥐",
    ) {
        App()
    }
}