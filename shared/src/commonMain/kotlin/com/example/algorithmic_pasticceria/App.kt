package com.example.algorithmic_pasticceria

import androidx.compose.animation.Crossfade
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.algorithmic_pasticceria.presentation.map.BakeryMapScreen
import com.example.algorithmic_pasticceria.presentation.levels.QueueSortingScreen
import com.example.algorithmic_pasticceria.presentation.navigation.Screen

@Composable
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.BakeryFloor) }

        Crossfade(targetState = currentScreen) { screen ->
            when (screen) {
                is Screen.BakeryFloor -> BakeryMapScreen(onNavigateTo = { currentScreen = it })
                is Screen.CustomerQueue -> QueueSortingScreen(onBack = { currentScreen = Screen.BakeryFloor })
                is Screen.CashRegister -> BakeryMapScreen(onNavigateTo = { currentScreen = it })
                is Screen.PastryShowcase -> BakeryMapScreen(onNavigateTo = { currentScreen = it })
            }
        }
    }
}