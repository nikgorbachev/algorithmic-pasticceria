package com.example.algorithmic_pasticceria

import androidx.compose.animation.Crossfade
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.algorithmic_pasticceria.presentation.map.BakeryMapScreen
import com.example.algorithmic_pasticceria.presentation.levels.SortingChapterMenuScreen
import com.example.algorithmic_pasticceria.presentation.levels.SortingNotebookScreen
import com.example.algorithmic_pasticceria.presentation.navigation.Screen

@Composable
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.BakeryFloor) }

        Crossfade(targetState = currentScreen) { screen ->
            when (screen) {
                is Screen.BakeryFloor -> BakeryMapScreen(
                    onNavigateTo = { requestedScreen -> currentScreen = requestedScreen }
                )
                is Screen.SortingChapterMenu -> SortingChapterMenuScreen(
                    onSelectAlgorithm = { algoId -> currentScreen = Screen.SortingLesson(algoId) },
                    onBack = { currentScreen = Screen.BakeryFloor }
                )
                is Screen.SortingLesson -> SortingNotebookScreen(
                    algorithmId = screen.algorithmId,
                    onBack = { currentScreen = Screen.SortingChapterMenu }
                )
                is Screen.CashRegister -> BakeryMapScreen(
                    onNavigateTo = { currentScreen = it }
                )
                is Screen.PastryShowcase -> BakeryMapScreen(
                    onNavigateTo = { currentScreen = it }
                )
            }
        }
    }
}